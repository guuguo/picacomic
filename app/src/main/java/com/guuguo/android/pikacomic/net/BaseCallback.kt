package com.guuguo.android.pikacomic.net

import com.google.gson.reflect.TypeToken
import com.guuguo.android.lib.net.LBaseCallback
import java.io.IOException
import java.lang.Exception

/**
 * guodeqing 创造于 16/6/4.
 * 项目 youku
 */
abstract class BaseCallback<T> : LBaseCallback<T>() {

    var typeToken: TypeToken<ResponseModel<T>>? = null
    fun initTypeToken(typeToken: TypeToken<ResponseModel<T>>) {
        this.typeToken = typeToken
    }

    override fun gsonExchange(result: String) {
        var model: ResponseModel<T>?
        if (typeToken == null)
            onApiLoadError("没有设置typeToken")
        else {
            try {
                model = ResponseModel.getResponseModel<T>(gson, typeToken!!, result)
                if (model.code != 200) {
                    onError(IOException(model.detail))
                    return
                }
            } catch (e: Exception) {
                onApiLoadError(result)
                return;
            }
            onApiLoadSuccess(model.data)
        }
    }
}
