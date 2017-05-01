package com.guuguo.android.pikacomic.net

import com.guuguo.android.lib.net.ApiServer
import com.guuguo.android.lib.net.LBaseCallback
import com.guuguo.android.pikacomic.constant.LocalData
import java.util.*

/**
 * guode 创造于 2017-05-01.
 * 项目 pika
 */

fun ApiServer.request(httpUrl: String, hashMap: HashMap<String, String>, callbackL: LBaseCallback<*>, isLogin: Boolean = false) {
    val headers = HashMap<String, String>()
    headers.put("api-key", "C69BAF41DA5ABD1FFEDC6D2FEA56B")
    headers.put("accept", "application/vnd.picacomic.com.v1+json")
    headers.put("app-version", "2.0.3.13")
    headers.put("app-uuid", UUID.randomUUID().toString())
    headers.put("app-platform", "android")
    headers.put("app-build-version", "29")
    headers.put("User-Agent", "okhttp/3.2.0")
    if (!isLogin) {
        headers.put("authorization", LocalData.token)
    }
    ApiServer.apiPost(httpUrl, "", headers, null, hashMap, callbackL)
}