package com.guuguo.android.pikacomic.constant

import android.support.v4.content.ContextCompat
import com.google.gson.GsonBuilder
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.MyApplication

/**
 * guode 创造于 2017-05-01.
 * 项目 pika
 */
val dataPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
val myGson by lazy {
    GsonBuilder().setDateFormat(dataPattern).create()
}
val loadingPlaceHolder by lazy { ContextCompat.getDrawable(MyApplication.instance, R.drawable.placeholder_loading) }
