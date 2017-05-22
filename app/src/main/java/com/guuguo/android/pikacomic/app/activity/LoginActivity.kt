package com.guuguo.android.pikacomic.app.activity

import android.databinding.DataBindingUtil
import com.flyco.systembar.SystemBarHelper
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.viewModel.LoginViewModel
import com.guuguo.android.pikacomic.base.BaseActivity
import com.guuguo.android.pikacomic.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    lateinit var binding: ActivityLoginBinding
    val viewModel = LoginViewModel(this)

    override fun getLayoutResId() = R.layout.activity_login
    override fun getHeaderTitle() = "登录"
    override fun isNavigationBack() = false

    override fun setLayoutResId(layoutResId: Int) {
        binding = DataBindingUtil.setContentView(activity, layoutResId)
        binding.viewModel = viewModel
    }

    override fun initView() {
        SystemBarHelper.immersiveStatusBar(activity, 0f)
        binding.edtUsername.setText("1152168009@qq.com")
        binding.edtPassword.setText("200996GDQ")

        rtv_login.setOnClickListener({
            if (checkValidate()) {
                viewModel.sign_in(binding.edtUsername.text.toString(), binding.edtPassword.text.toString())
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
