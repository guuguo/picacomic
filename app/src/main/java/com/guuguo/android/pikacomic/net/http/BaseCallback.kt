package com.guuguo.android.pikacomic.net.http

import android.accounts.NetworkErrorException
import com.guuguo.android.R
import com.guuguo.android.lib.BaseApplication
import com.guuguo.android.lib.extension.safe
import io.reactivex.SingleObserver
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * guodeqing 创造于 16/6/4.
 * 项目 youku
 */
abstract class BaseCallback<T> : SingleObserver<T> {
    override fun onSuccess(t: T) {
        val resModel = t as ResponseModel<*>
        if (resModel.code != 200) {
            onError(IOException(resModel.detail))
            return
        }
    }

    override fun onError(e: Throwable?) {
        if (e != null) {
            when (e) {
                is SocketTimeoutException -> onApiLoadError(BaseApplication.get().getString(R.string.state_network_timeout))
                is NetworkErrorException -> onApiLoadError(BaseApplication.get().getString(R.string.state_network_error))
                is UnknownHostException -> onApiLoadError(BaseApplication.get().getString(R.string.state_network_unknown_host))
                is ConnectException -> onApiLoadError(BaseApplication.get().getString(R.string.state_network_unknown_host))
                is HttpException -> onApiLoadError(when (e.code()) {
                    400 -> "Bad Request 请求出现语法错误"
                    401 -> "Unauthorized 访问被拒绝"
                    403 -> "Forbidden 资源不可用"
                    404 -> "Not Found 无法找到指定位置的资源"
                    405 -> "Method Not Allowed 请求方法（GET、POST、HEAD、Delete、PUT、TRACE等）对指定的资源不适用"
                    in 406..499 -> "客户端错误"
                    500 -> "Internal Server Error 服务器遇到了意料不到的情况，不能完成客户的请求"
                    502 -> "Bad Gateway .Web 服务器用作网关或代理服务器时收到了无效响应"
                    503 -> "Service Unavailable 服务不可用,服务器由于维护或者负载过重未能应答"
                    504 -> "Gateway Timeout 网关超时"
                    505 -> "HTTP Version Not Supported 服务器不支持请求中所指明的HTTP版本"
                    in 506..599 -> "服务端错误"
                    else -> e.message().safe()
                })
                is IOException -> onApiLoadError(e.message.safe())
                else -> onApiLoadError(e.message.safe())
            }
        }
    }

    abstract fun onApiLoadError(msg: String)

}
