package com.guuguo.android.pikacomic.constant

import com.google.gson.GsonBuilder
import com.guuguo.android.lib.net.LBaseCallback

/**
 * guode 创造于 2017-05-01.
 * 项目 pika
 */
val normalDate = "yyyy-MM-dd'T'hh:mm:ss.S'Z'"
val gson = GsonBuilder().setDateFormat(LBaseCallback.normalDate).create()
