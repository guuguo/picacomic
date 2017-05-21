package com.guuguo.android.pikacomic.app.activity

import android.databinding.DataBindingUtil
import com.flyco.systembar.SystemBarHelper
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.base.BaseActivity
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.databinding.ActivityLoginBinding
import com.guuguo.android.pikacomic.entity.TokenResponse
import com.guuguo.android.pikacomic.net.BaseCallback
import com.guuguo.android.pikacomic.net.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    lateinit var binding : ActivityLoginBinding

    override fun getLayoutResId()= R.layout.activity_login
    override fun getHeaderTitle()="登录"
    override fun isNavigationBack() = false

    override fun setContentView(layoutResID: Int) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    override fun initView() {
        SystemBarHelper.immersiveStatusBar(activity,0f)
        binding.edtUsername.setText("1152168009@qq.com")
        binding.edtPassword.setText("200996GDQ")

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
