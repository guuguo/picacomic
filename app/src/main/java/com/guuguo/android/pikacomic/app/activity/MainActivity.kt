package com.guuguo.android.pikacomic.app.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.flyco.systembar.SystemBarHelper
import com.flyco.tablayout.listener.CustomTabEntity
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.fragment.CategoryFragment
import com.guuguo.android.pikacomic.app.fragment.HomeFragment
import com.guuguo.android.pikacomic.app.fragment.MineFragment
import com.guuguo.android.pikacomic.app.fragment.SettingFragment
import com.guuguo.android.pikacomic.base.BaseActivity
import com.guuguo.android.pikacomic.databinding.ActivityMainBinding


/**
 * guode 创造于 2017-05-01.
 * 项目 pika
 */
class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding

    val STATE_FRAGMENT_SHOW = "STATE_FRAGMENT_SHOW"
    override val isBackExit=true
    private val mTitles = arrayOf("首页", "分类", "我的", "设置")
    private val mUnselectedIcons = arrayOf(R.drawable.ic_homepage, R.drawable.ic_manage, R.drawable.ic_mine, R.drawable.ic_setup)
    private val mSelectedIcons = arrayOf(R.drawable.ic_homepage_fill, R.drawable.ic_manage_fill, R.drawable.ic_mine_fill, R.drawable.ic_setup_fill)
    protected var mFragments = ArrayList<Fragment>()

    override fun getLayoutResId() = R.layout.activity_main
    override fun setLayoutResId(layoutResId: Int) {
        binding = DataBindingUtil.setContentView(activity, layoutResId)
    }

    companion object {
        fun intentTo(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        mFragments.add(HomeFragment())
        mFragments.add(CategoryFragment())
        mFragments.add(MineFragment())
        mFragments.add(SettingFragment())
    }

    override fun initView() {
        super.initView()
        SystemBarHelper.tintStatusBar(activity,ContextCompat.getColor(activity,R.color.colorPrimary),0f)
        binding.tabMain.setTabData(getTabEntities() as java.util.ArrayList<CustomTabEntity>, activity, R.id.content, mFragments)
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
}