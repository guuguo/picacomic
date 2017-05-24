package com.guuguo.android.pikacomic.net.http

import android.accounts.NetworkErrorException
import com.guuguo.android.R
import com.guuguo.android.lib.BaseApplication
import com.guuguo.android.lib.extension.safe
import io.reactivex.SingleObserver
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
                is IOException -> onApiLoadError(e.message.safe())
                else -> onApiLoadError(e.message.safe())
            }
        }
    }

    abstract fun onApiLoadError(msg: String)

}
