package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import com.guuguo.android.pikacomic.app.activity.LoginActivity
import com.guuguo.android.pikacomic.app.activity.MainActivity
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.entity.TokenResponse
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable

/**
 * guode 创造于 2017-05-21.
 * 项目 pika
 */
class LoginViewModel(val activity: LoginActivity) : BaseObservable() {
    fun sign_in(userName:String,password:String) {
        activity.dialogLoadingShow("正在登录中")
        MyApiServer.signIn(userName,password).subscribe(object : BaseCallback<ResponseModel<TokenResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<TokenResponse>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                t.data?.let {
                    LocalData.token = t.data!!.token
                    LocalData.isLogin = true
                    LocalData.username = userName
                    LocalData.password = password
                    MainActivity.intentTo(activity)
                }
            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                activity.dialogErrorShow(msg, null)
            }
        })
    }
}