package com.guuguo.android.pikacomic.app.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.flyco.systembar.SystemBarHelper
import com.guuguo.android.lib.utils.isEmail
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.viewModel.LoginViewModel
import com.guuguo.android.pikacomic.base.BaseActivity
import com.guuguo.android.pikacomic.constant.LocalData
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

    override fun initStatusBar() {
        SystemBarHelper.immersiveStatusBar(activity, 0f)
    }

    override fun initView() {
        if (LocalData.isLogin) {
            MainActivity.intentTo(activity)
        }
        fillEdit()

        rtv_login.setOnClickListener({
            signIn()
        })
        binding.edtPassword.setOnEditorActionListener(TextView.OnEditorActionListener
        { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                signIn()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun signIn() {
        if (checkValidate()) {
            viewModel.sign_in(binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
        }
    }

    fun fillEdit() {
        binding.edtEmail.setText(LocalData.email)
        binding.edtPassword.setText(LocalData.password)
    }

    private fun checkValidate(): Boolean {
        if (binding.edtEmail.text.isEmpty()) {
            binding.edtEmail.error = "邮箱未输入"
            return false
        } else if (!binding.edtEmail.text.toString().isEmail()) {
            binding.edtEmail.error = "邮箱格式不正确"
            return false
        } else if (binding.edtPassword.text.isEmpty()) {
            binding.edtPassword.error = "密码未输入"
            return false
        } else if (binding.edtPassword.text.length < 8) {
            binding.edtPassword.error = "密码至少要8位"
            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.MAIN_ACTIVITY && resultCode == Activity.RESULT_OK) {
        } else if (requestCode == MainActivity.MAIN_ACTIVITY && resultCode == Activity.RESULT_CANCELED) {
            finish()
        } else if (requestCode == RegisterFragment.REGISTER_FRAGMENT && resultCode == Activity.RESULT_OK) {
            fillEdit()
        }
    }

}
