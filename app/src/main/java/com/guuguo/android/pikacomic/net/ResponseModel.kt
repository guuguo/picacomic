package com.guuguo.android.pikacomic.net

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

        fun <T> getResponseModel(dataType: TypeToken<ResponseModel<T>>, json: String): ResponseModel<T> {
            return Gson().fromJson<ResponseModel<T>>(json, dataType.type)
        }

        fun <T> getResponseModel(gson: Gson, dataType: TypeToken<ResponseModel<T>>, json: String): ResponseModel<T> {
            return gson.fromJson<ResponseModel<T>>(json, dataType.type)
        }
    }
}