package com.guuguo.android.pikacomic.app.activity

import android.support.v4.content.ContextCompat
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.base.BaseActivity
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.entity.TokenResponse
import com.guuguo.android.pikacomic.net.BaseCallback
import com.guuguo.android.pikacomic.net.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.base_toolbar_common.*

class LoginActivity : BaseActivity() {
    
    override fun getLayoutResId()= R.layout.activity_login
    override fun getToolBar()= id_tool_bar
    override fun getHeaderTitle()="登录"
    override fun isNavigationBack() = false
    
    override fun initView() {
        id_tool_bar.navigationIcon = ContextCompat.getDrawable(activity, R.drawable.ic_login)
        edt_username.setText("1152168009@qq.com")
        edt_password.setText("200996GDQ")

        rtv_login.setOnClickListener({
            if (checkValidate()) {
                dialogLoadingShow("正在登录中")
                MyApiServer.signIn(edt_username.text.toString(), edt_password.text.toString()).subscribe(object : BaseCallback<ResponseModel<TokenResponse>>() {
                    override fun onSubscribe(d: Disposable?) {
                        addApiCall(d)
                    }

                    override fun onSuccess(t: ResponseModel<TokenResponse>) {
                        super.onSuccess(t)
                        dialogDismiss()
                        t.data?.let {
                            LocalData.token = t.data!!.token
                            dialogCompleteShow(LocalData.token, null, 1200)
                        }
                    }

                    override fun onApiLoadError(msg: String) {
                        dialogDismiss()
                        activity.dialogErrorShow(msg, null)
                    }
                })
            }
        })
    }

    private fun checkValidate(): Boolean {
        if (edt_username.text.isEmpty()) {
            dialogErrorShow("邮箱未输入", null)
            return false
        } else if (edt_password.text.isEmpty()) {
            dialogErrorShow("密码未输入", null)
            return false
        }
        return true
    }


}
