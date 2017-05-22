package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import com.guuguo.android.pikacomic.R.id.edt_password
import com.guuguo.android.pikacomic.R.id.edt_username
import com.guuguo.android.pikacomic.app.activity.LoginActivity
import com.guuguo.android.pikacomic.app.activity.MainActivity
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.entity.TokenResponse
import com.guuguo.android.pikacomic.net.BaseCallback
import com.guuguo.android.pikacomic.net.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*

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