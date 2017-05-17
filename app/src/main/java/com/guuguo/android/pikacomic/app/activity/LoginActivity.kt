package com.guuguo.android.pikacomic.app.activity

import android.support.v4.content.ContextCompat
import android.view.MenuItem
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.base.BaseActivity
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.entity.TokenResponse
import com.guuguo.android.pikacomic.net.BaseCallback
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_login
    }

    override fun getHeaderTitle(): String {
        return "登录"
    }

    override fun isNavigationButtonVisible(): Boolean {
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return false
    }

    override fun initView() {
        super.initView()
        toolbar.navigationIcon = ContextCompat.getDrawable(activity, R.drawable.ic_icecream)
        edt_username.setText("1152168009@qq.com")
        edt_password.setText("200996GDQ")

        rtv_login.setOnClickListener({
            if (checkValidate()) {
                dialogLoadingShow("正在登录中")
                ApiLoginServer.login(edt_username.text.toString(), edt_password.text.toString(), object : BaseCallback<TokenResponse>() {
                    override fun onSubscribe(d: Disposable?) {
                        addApiCall(d)
                    }

                    override fun onApiLoadSuccess(model: TokenResponse?) {
                        dialogDismiss()
                        LocalData.token = model!!.token
                        dialogCompleteShow(LocalData.token, null, 1200)
                    }

                    override fun onApiLoadError(msg: String) {
                        dialogDismiss()
                        activity.dialogErrorShow(msg, null)
                    }
                })
            }
        })
    }

    private fun checkValidate(): Boolean {
        if (edt_username.text.isEmpty()) {
            dialogErrorShow("邮箱未输入", null)
            return false
        } else if (edt_password.text.isEmpty()) {
            dialogErrorShow("密码未输入", null)
            return false
        }
        return true
    }


}
