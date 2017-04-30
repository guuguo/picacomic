package com.guuguo.android.pikacomic.app

import android.support.v7.app.AppCompatDelegate
import com.guuguo.android.lib.BaseApplication

/**
 * Created by guodeqing on 16/3/7.
 */
class MyApplication : BaseApplication() {
    override fun init() {
        instance = INSTANCE as MyApplication?;
        initBugly()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); }

    companion object {
        var instance: MyApplication? = null
    }


    private fun initBugly() {
//        Beta.upgradeDialogLayoutId = R.layout.dialog_upgrade
//        Beta.tipsDialogLayoutId = R.layout.dialog_tips
//        Bugly.init(this, "40950003e9", false);
    }
}
