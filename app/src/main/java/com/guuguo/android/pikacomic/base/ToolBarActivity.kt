//package com.guuguo.gank.app.base
//
//
//import android.os.Bundle
//import android.support.design.widget.AppBarLayout
//import android.support.v7.app.ActionBar
//import android.support.v7.widget.Toolbar
//import android.view.animation.DecelerateInterpolator
//import com.guuguo.gank.R
//import kotterknife.bindView
//
///**
// * 带Toolbar的基础Activity
// * Created by panl on 16/1/5.
// */
//abstract class ToolBarActivity : BaseActivity() {
//    protected var actionBar: ActionBar? = null
//    protected var isToolBarHiding = false
//
//    val toolbar: Toolbar by bindView(R.id.toolbar)
//    val appBar: AppBarLayout by bindView(R.id.app_bar)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initToolBar()
//    }
//
//    protected fun canBack(): Boolean {
//        return true
//    }
//
//    protected fun initToolBar() {
//        setSupportActionBar(toolbar)
//        actionBar = supportActionBar
//        if (actionBar != null) actionBar!!.setDisplayHomeAsUpEnabled(canBack())
//
//    }
//
//    protected fun hideOrShowToolBar() {
//        appBar.animate().translationY((if (isToolBarHiding) 0 else -appBar!!.height).toFloat()).setInterpolator(DecelerateInterpolator(2f)).intentStart()
//        isToolBarHiding = !isToolBarHiding
//    }
//}
