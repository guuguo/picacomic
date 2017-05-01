package com.guuguo.android.pikacomic.net

import com.google.gson.reflect.TypeToken
import com.guuguo.android.lib.net.ApiServer
import com.guuguo.android.pikacomic.entity.TokenResponse

/**
 * guode 创造于 2017-05-01.
 * 项目 pika
 */
object ApiLoginServer {
    fun login(email: String, password: String, callback: BaseCallback<TokenResponse>) {
        callback.initTypeToken(object : TypeToken<ResponseModel<TokenResponse>>() {})
        val url = "https://picaapi.picacomic.com/auth/sign-in"
        val map = HashMap<String, String>()
        map.put("email", email)
        map.put("password", password)
        ApiServer.request(url, map, callback)
    }

}