package com.guuguo.android.pikacomic.app

import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate

/**
 * Created by guodeqing on 16/3/7.
 */
class MyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        instance = this;
        initBugly()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); 
    }

    companion object {
        var instance: MyApplication? = null
    }


    private fun initBugly() {
//        Beta.upgradeDialogLayoutId = R.layout.dialog_upgrade
//        Beta.tipsDialogLayoutId = R.layout.dialog_tips
//        Bugly.init(this, "40950003e9", false);
    }
}
