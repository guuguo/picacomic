package com.guuguo.android.pikacomic.app.activity

import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import com.flyco.systembar.SystemBarHelper
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.base.BaseActivity
import kotlinx.android.synthetic.main.layout_title_bar.*

class BaseTitleFragmentActivity : BaseActivity() {

    override fun getLayoutResId() = R.layout.activity_title_fragment
    override fun isNavigationBack() = true
    override fun getToolBar(): Toolbar? {
        return id_toolbar
    }

    override fun initToolBar() {
        super.initToolBar()
        getToolBar()?.navigationIcon = ContextCompat.getDrawable(activity, R.drawable.ic_arrowleft)
        supportActionBar?.title = ""
    }

    override fun getAppBar(): AppBarLayout? {
        return appbar
    }
    override fun setTitle(title: CharSequence?) {
        tv_title_bar.text = title
    }

    override fun initView() {
        super.initView()
        SystemBarHelper.tintStatusBar(activity, ContextCompat.getColor(activity, R.color.colorPrimary), 0f)
    }

}
