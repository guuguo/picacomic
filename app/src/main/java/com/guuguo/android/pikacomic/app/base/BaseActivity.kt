package com.guuguo.android.pikacomic.app.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.WindowManager
import com.guuguo.android.lib.utils.ScreenManager
import com.guuguo.android.pikacomic.app.MyApplication

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseActivity : AppCompatActivity() {
    protected var myApplication = MyApplication.instance
    /*init*/
    protected abstract fun init()

    open protected fun initVariable(savedInstanceState: Bundle?) {}

    /*setting*/
    open protected fun getHeaderTitle() = ""

    open protected fun getToolBar(): Toolbar? = null
    open protected fun isNavigationButtonVisible() = true

    /*onCreate*/
    protected abstract fun getLayoutResId(): Int

    val activity = this
    
    open protected fun isFullScreen() = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isTaskRoot
                && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                && intent.action != null
                && intent.action == Intent.ACTION_MAIN) {
            finish()
            return
        }
        //入栈到pushActivity      
        ScreenManager.pushActivity(this)
        if (isFullScreen()) {
            val flag = WindowManager.LayoutParams.FLAG_FULLSCREEN
            val window = this.window
            window.setFlags(flag, flag)
        }

        setContentView(getLayoutResId())
        setSupportActionBar(getToolBar())
        initVariable(savedInstanceState)
        init()
    }
    /*toolbar*/

    /*menu*/
    open protected fun getMenuResId() = 0

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (getMenuResId() != 0)
            menuInflater.inflate(getMenuResId(), menu)
        return super.onCreateOptionsMenu(menu)
    }
}
