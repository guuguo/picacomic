package com.guuguo.android.pikacomic.app.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.flyco.systembar.SystemBarHelper
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.guuguo.android.lib.app.LNBaseFragment
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.fragment.*
import com.guuguo.android.pikacomic.base.BaseActivity
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.databinding.ActivityMainBinding


/**
 * guode 创造于 2017-05-01.
 * 项目 pika
 */
class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding

    override val backExitStyle = BACK_DIALOG_CONFIRM
    private val mTitles = arrayOf("首页", "分类", "我的", "设置")
    private val mUnselectedIcons = arrayOf(R.drawable.ic_homepage, R.drawable.ic_manage, R.drawable.ic_mine, R.drawable.ic_setup)
    private val mSelectedIcons = arrayOf(R.drawable.ic_homepage_fill, R.drawable.ic_manage_fill, R.drawable.ic_mine_fill, R.drawable.ic_setup_fill)
    var mFragments = ArrayList<LNBaseFragment>()

    override fun getLayoutResId() = R.layout.activity_main
    override fun setLayoutResId(layoutResId: Int) {
        binding = DataBindingUtil.setContentView(activity, layoutResId)
    }

    companion object {
        val MAIN_ACTIVITY = 0x1
        fun intentTo(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivityForResult(intent, MAIN_ACTIVITY)
        }
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        if (savedInstanceState == null) {
            mFragments.add(HomeFragment())
            mFragments.add(CategoryFragment())
            mFragments.add(MineFragment())
            mFragments.add(SettingFragment())
            loadMultipleRootFragment(R.id.content, 0, *mFragments.toTypedArray())
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[0] = findFragment(HomeFragment::class.java)
            mFragments[1] = findFragment(CategoryFragment::class.java)
            mFragments[2] = findFragment(MineFragment::class.java)
            mFragments[3] = findFragment(SettingFragment::class.java)
        }
    }

    override fun initView() {
        super.initView()
        if (LocalData.patternStr.isNullOrEmpty())
            lateInitView()
        else {
            PatternLockFragment.intentTo(activity, PatternLockFragment.TYPE_UNLOCK, true)
        }
    }

    fun lateInitView() {
        SystemBarHelper.tintStatusBar(activity, ContextCompat.getColor(activity, R.color.colorPrimary), 0f)
        binding.tabMain.setTabData(getTabEntities() as java.util.ArrayList<CustomTabEntity>)
        binding.tabMain.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(p0: Int) {
                showHideFragment(mFragments[p0], mFragment)
                mFragment=mFragments[p0]
            }

            override fun onTabReselect(p0: Int) {
            }

        })
        binding.tabMain.currentTab = 0
    }

    fun getTabEntities(): ArrayList<TabEntity> {
        val array = ArrayList<TabEntity>()
        (0..mTitles.size - 1).mapTo(array) { TabEntity(mTitles[it], mSelectedIcons[it], mUnselectedIcons[it]) }
        return array
    }

    inner class TabEntity(var title: String, var selectedIcon: Int, var unSelectedIcon: Int) : CustomTabEntity {
        override fun getTabTitle(): String {
            return title
        }

        override fun getTabSelectedIcon(): Int {
            return selectedIcon
        }

        override fun getTabUnselectedIcon(): Int {
            return unSelectedIcon
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PatternLockFragment.PATTERN_LOCK_FRAGMENT_FROM_MAIN_ACTIVITY && resultCode == Activity.RESULT_OK) {
            lateInitView()
        } else if (requestCode == PatternLockFragment.PATTERN_LOCK_FRAGMENT_FROM_MAIN_ACTIVITY && resultCode == Activity.RESULT_CANCELED) {
//            finish()
        }
    }

}