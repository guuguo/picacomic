package com.guuguo.android.pikacomic.constant

import com.google.gson.GsonBuilder
import com.guuguo.android.pikacomic.app.MyApplication

/**
 * guode 创造于 2017-05-01.
 * 项目 pika
 */
val dataPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
val myGson by lazy {
    GsonBuilder().setDateFormat(dataPattern).create()
}
const val baseUrl = "https://picaapi.picacomic.com"
const val jsonDataFormatStr = "yyyy-MM-dd hh:mm:ss.0"
fun getFileDir() = MyApplication.instance!!.filesDir.absolutePath!!

const val BUS_ACTION_URL_DOWNLOAD="BUS_ACTION_URL_DOWNLOAD"