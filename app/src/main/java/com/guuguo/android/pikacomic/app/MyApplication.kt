package com.guuguo.android.pikacomic.app

import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatDelegate
import com.guuguo.android.lib.BaseApplication
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.constant.getFileDir
import com.guuguo.gank.net.MyRetrofit.myRetrofit
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import zlc.season.rxdownload2.RxDownload

/**
 * Created by guodeqing on 16/3/7.
 */
class MyApplication : BaseApplication() {
    override fun init() {
        instance = this;
        MultiDex.install(this)
        initBugly()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        initRxDownload()
    }

    private fun initRxDownload() {
        RxDownload.getInstance(this)
                .retrofit(myRetrofit)             //若需要自己的retrofit客户端,可在这里指定
                .defaultSavePath(getFileDir()) //设置默认的下载路径
                .maxThread(5)                     //设置最大线程
                .maxRetryCount(4)                 //设置下载失败重试次数
                .maxDownloadNumber(4)             //Service同时下载数量
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
