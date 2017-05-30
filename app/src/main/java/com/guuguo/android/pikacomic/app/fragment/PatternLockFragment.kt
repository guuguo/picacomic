package com.guuguo.android.pikacomic.app.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import com.guuguo.android.lib.app.LNBaseActivity
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.activity.BaseTitleFragmentActivity
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.constant.LocalData
import com.pro100svitlo.fingerprintAuthHelper.FahErrorType
import com.pro100svitlo.fingerprintAuthHelper.FahListener
import com.pro100svitlo.fingerprintAuthHelper.FingerprintAuthHelper
import kotlinx.android.synthetic.main.fragment_pattern.*


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class PatternLockFragment : BaseFragment(), FahListener {

    override fun getLayoutResId() = R.layout.fragment_pattern
    private var mFAH: FingerprintAuthHelper? = null

    var type = TYPE_CREATE_PATTERN
    var firstPatternStr = ""

    override fun getHeaderTitle(): String {
        return when (type) {
            TYPE_CREATE_PATTERN -> "设置图案密码"
            else -> "验证图案密码"
        }
    }

    companion object {
        val PATTERN_LOCK_FRAGMENT = 0x44
        val PATTERN_LOCK_FRAGMENT_FROM_MAIN_ACTIVITY = 0x9
        val ARG_PATTERN_TYPE = "ARG_PATTERN_TYPE"
        val TYPE_CREATE_PATTERN = 0
        val TYPE_UNLOCK = 1
        val TYPE_CANCEL_PATTERN = 2
        fun intentTo(activity: Activity, type: Int, isFromMain: Boolean = false) {
            val intent = Intent(activity, BaseTitleFragmentActivity::class.java)
            intent.putExtra(LNBaseActivity.SIMPLE_ACTIVITY_INFO, PatternLockFragment::class.java)

            val bundle = Bundle()
            bundle.putInt(ARG_PATTERN_TYPE, type)
            intent.putExtras(bundle)
            if (isFromMain)
                activity.startActivityForResult(intent, PATTERN_LOCK_FRAGMENT_FROM_MAIN_ACTIVITY)
            else
                activity.startActivityForResult(intent, PATTERN_LOCK_FRAGMENT)
        }
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        type = arguments.getInt(ARG_PATTERN_TYPE)
    }

    override fun initView() {
        super.initView()
        if (type == TYPE_UNLOCK) {
            mFAH = FingerprintAuthHelper.Builder(activity, this).build()
            if (mFAH!!.isHardwareEnable) {
                iv_fingerprint.visibility = View.VISIBLE
            } else {
                iv_fingerprint.visibility = View.GONE

            }
        }
        tv_point.text = "绘制解锁图案"
        val mPatternLockViewListener = object : PatternLockViewListener {
            override fun onStarted() {
                tv_point.text = "完成后松开手指"
            }

            override fun onProgress(progressPattern: List<PatternLockView.Dot>) {
            }

            override fun onComplete(pattern: List<PatternLockView.Dot>) {
                val tempPattern = PatternLockUtils.patternToString(pattern_lock_view, pattern)
                if (pattern.size < 4) {
                    pattern_lock_view.setViewMode(PatternLockView.PatternViewMode.WRONG)
                    tv_point.text = "至少需链接4个点，请重试"
                } else when (type) {
                    TYPE_CREATE_PATTERN -> {
                        if (firstPatternStr.isNullOrEmpty()) {
                            pattern_lock_view.clearPattern()
                            tv_point.text = "再次绘制图案进行确认"
                            firstPatternStr = tempPattern
                        } else if (firstPatternStr == tempPattern) {
                            tv_point.text = "您的新解锁图案"
                            LocalData.patternStr = tempPattern
                            tv_point.postDelayed({
                                activity.setResult(RESULT_OK)
                                activity.finish()
                            }, 200)
                        } else
                            tv_point.text = "图案错误"
                    }
                    TYPE_CANCEL_PATTERN -> {
                        if (tempPattern == LocalData.patternStr) {
                            activity.setResult(RESULT_OK)
                            activity.finish()
                        } else {
                            tv_point.text = "图案错误"
                            pattern_lock_view.setViewMode(PatternLockView.PatternViewMode.WRONG)
                        }
                    }
                    TYPE_UNLOCK -> {
                        if (tempPattern == LocalData.patternStr) {
                            activity.setResult(RESULT_OK)
                            activity.finish()
                        } else {
                            tv_point.text = "图案错误"
                            pattern_lock_view.setViewMode(PatternLockView.PatternViewMode.WRONG)
                        }
                    }
                }
            }

            override fun onCleared() {
            }
        }
        pattern_lock_view.addPatternLockListener(mPatternLockViewListener)
    }

    override fun onBackPressed(): Boolean {
        if (type == TYPE_UNLOCK) {
            activity.exit()
            return true
        } else {
            return super.onBackPressed()
        }
    }

    override fun onFingerprintListening(listening: Boolean, milliseconds: Long) {
        if (listening) {
            //add some code here
        } else {
            //add some code here
        }
        if (milliseconds > 0) {
            //if u need, u can show timeout for user
        }
    }

    override fun onFingerprintStatus(authSuccessful: Boolean, errorType: Int, errorMess: CharSequence?) {
        if (authSuccessful) {
            tv_pinter_point.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryBlue))
            tv_pinter_point.text = "识别正确"
            activity.setResult(RESULT_OK)
            activity.finish()
        } else if (mFAH != null) {
            tv_pinter_point.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryRed))
            when (errorType) {
                FahErrorType.General.LOCK_SCREEN_DISABLED, FahErrorType.General.NO_FINGERPRINTS -> mFAH?.showSecuritySettingsDialog()
                FahErrorType.Auth.AUTH_NOT_RECOGNIZED -> {
                    tv_pinter_point.text = "无法识别"
                }
                FahErrorType.Auth.AUTH_TOO_MANY_TRIES -> {
                    tv_pinter_point.text = "错误次数太多，请使用图案解锁"
                }
            }
        }
    }

    override fun onResume() {
        super.onResume();
        mFAH?.startListening();
    }

    override fun onStop() {
        super.onStop();
        mFAH?.stopListening();
    }

    override fun onDestroy() {
        super.onDestroy();
        mFAH?.onDestroy();
    }

}