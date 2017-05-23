package com.guuguo.android.pikacomic.net.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by guodeqing on 16/3/12.
 */
class ResponseModel<T>  {
    var code: Int = 0
    var message: String = ""
    var error: String = ""
    var detail: String = ""
    var data: T? = null

    companion object {

        fun <T> getResponseModel(dataType: com.google.gson.reflect.TypeToken<ResponseModel<T>>, json: String): com.guuguo.android.pikacomic.net.http.ResponseModel<T> {
            return com.google.gson.Gson().fromJson<com.guuguo.android.pikacomic.net.http.ResponseModel<T>>(json, dataType.type)
        }

        fun <T> getResponseModel(gson: com.google.gson.Gson, dataType: com.google.gson.reflect.TypeToken<ResponseModel<T>>, json: String): com.guuguo.android.pikacomic.net.http.ResponseModel<T> {
            return gson.fromJson<com.guuguo.android.pikacomic.net.http.ResponseModel<T>>(json, dataType.type)
        }
    }
}