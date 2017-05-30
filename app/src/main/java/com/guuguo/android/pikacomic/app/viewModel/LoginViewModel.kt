package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import android.text.InputType
import android.view.View
import com.flyco.dialog.listener.OnBtnClickL
import com.guuguo.android.lib.utils.isEmail
import com.guuguo.android.lib.view.dialog.EditAlertDialog
import com.guuguo.android.pikacomic.app.activity.LoginActivity
import com.guuguo.android.pikacomic.app.activity.MainActivity
import com.guuguo.android.pikacomic.app.activity.RegisterFragment
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
    fun sign_in(userName: String, password: String) {
        activity.dialogLoadingShow("正在登录中")
        MyApiServer.signIn(userName, password).subscribe(object : BaseCallback<ResponseModel<TokenResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<TokenResponse>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                t.data?.let {
                    LocalData.token = t.data!!.token
                    LocalData.isLogin = true
                    LocalData.email = userName
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

    val TYPE_FORGOT_PASSWORD = 0
    val TYPE_RESEND_ACTIVATION = 1

    private fun sendEmail(email: String, type: Int) {
        val sub = if (type == TYPE_FORGOT_PASSWORD)
            MyApiServer.forgot_password(email)
        else if (type == TYPE_RESEND_ACTIVATION)
            MyApiServer.resend_activation(email)
        else
            MyApiServer.resend_activation(email)
        activity.dialogLoadingShow("正在努力操作中")
        sub.subscribe(object : BaseCallback<ResponseModel<String>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<String>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                activity.dialogMsgShow("一封密函已送往你的邮箱(有可能在垃圾箱，并要等待24小时以上才能收到)，请跟随指示激活账号!", "关闭", null)
            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                activity.dialogErrorShow(msg, null)
            }
        })
    }

    fun onRegisterClick(view: View) {
        RegisterFragment.intentTo(activity)
    }

    fun onReActivationClick(view: View) {
        showSentEmailDialog(TYPE_RESEND_ACTIVATION)
    }

    fun onForgetPasswordClick(view: View) {
        showSentEmailDialog(TYPE_FORGOT_PASSWORD)
    }

    private fun showSentEmailDialog(type: Int) {
        val title: String
        if (type == TYPE_RESEND_ACTIVATION) {
            title = "重发激活邮件"
        } else {
            title = "忘记密码"
        }
        val edtDialog = EditAlertDialog(activity).title(title)
        edtDialog.setHint("填写邮箱地址")
                .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                .btnNum(2)
                .editText = LocalData.email
        edtDialog.setOnBtnClickL(OnBtnClickL { edtDialog.dismiss() },
                OnBtnClickL {
                    val email = edtDialog.editText
                    if (email.isEmpty())
                        edtDialog.setError("邮箱地址不能为空")
                    else if (!email.isEmail()) {
                        edtDialog.setError("邮箱格式不正确")
                    } else {
                        sendEmail(email, type)
                    }
                })
        edtDialog.show()
    }

}