package com.guuguo.android.pikacomic.app

import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatDelegate
import com.guuguo.android.lib.BaseApplication
import com.guuguo.android.pikacomic.R
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta

/**
 * Created by guodeqing on 16/3/7.
 */
class MyApplication : BaseApplication() {
    override fun init() {
        instance = this;
        MultiDex.install(this)
        initBugly()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    companion object {
        var instance: MyApplication? = null
    }


    private fun initBugly() {
        Beta.upgradeDialogLayoutId = R.layout.dialog_upgrade
        Beta.tipsDialogLayoutId = R.layout.dialog_tips
        Bugly.init(this, "b01f77bd60", false)
    }
}
