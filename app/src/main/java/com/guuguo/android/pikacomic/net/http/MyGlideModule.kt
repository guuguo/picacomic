package com.guuguo.android.pikacomic.net.http

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.GlideModule
import com.guuguo.gank.net.MyRetrofit
import java.io.InputStream

/**
 * mimi 创造于 2017-05-23.
 * 项目 pika
 */

class MyGlideModule : GlideModule {
    override fun registerComponents(context: Context, glide: Glide) {
        // 设置长时间读取和断线重连
        val client = MyRetrofit.httpClient
        glide.register(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(client))
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // 防止图片变绿，在有ALPHA通道的情况下
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888)
    }
}
