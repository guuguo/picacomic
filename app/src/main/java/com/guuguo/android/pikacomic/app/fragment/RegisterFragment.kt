package com.guuguo.android.pikacomic.app.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flyco.dialog.listener.OnBtnClickL
import com.guuguo.android.lib.app.LNBaseActivity
import com.guuguo.android.lib.extension.toast
import com.guuguo.android.lib.utils.isEmail
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.viewModel.RegisterViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment() {
    lateinit var binding: FragmentRegisterBinding
    val viewModel by lazy { RegisterViewModel(this) }

    override fun getLayoutResId() = R.layout.fragment_register
    override fun getHeaderTitle() = "注册"

    val male = "m"
    val female = "f"
    val robot = "bot"
    var gender_Str = male
    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    fun onRegisterClick() {
        if (checkValidate())
            activity.dialogWarningShow("你确定该邮箱地址可用吗?\n如果是假的,你有可能被哔咔漫画永久封锁哦!", "取消", "确定", OnBtnClickL {
                viewModel.register(binding.edtEmail.text.toString(), binding.edtPassword.text.toString(), gender_Str, binding.edtEmail.text.toString())
            })
    }

    companion object {
        val REGISTER_FRAGMENT = 0x7
        fun intentTo(activity: Activity) {
            val intent = Intent(activity, BaseTitleFragmentActivity::class.java)
            intent.putExtra(LNBaseActivity.SIMPLE_ACTIVITY_INFO, RegisterFragment::class.java)

            val bundle = Bundle()

            intent.putExtras(bundle)
            activity.startActivityForResult(intent, REGISTER_FRAGMENT)
        }
    }

    override fun initView() {
        super.initView()
        binding.rgSex.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_male -> gender_Str = male
                R.id.rb_female -> gender_Str = female
                R.id.rb_robot -> gender_Str = robot
            }
        }
        binding.rgSex.check(R.id.rb_male)
        binding.rtvRegister.setOnClickListener { onRegisterClick() }
    }

    fun checkValidate(): Boolean {
        if (binding.edtUsername.text.isEmpty()) {
            binding.edtUsername.error = "昵称未输入"
            return false
        } else if (binding.edtPassword.text.isEmpty()) {
            binding.edtPassword.error = "密码未输入"
            return false
        } else if (binding.edtEmail.text.isEmpty()) {
            binding.edtEmail.error = "邮箱未输入"
            return false
        } else if (!binding.edtEmail.text.toString().isEmail()) {
            binding.edtEmail.error = "邮箱格式不正确"
            return false
        } else if (binding.edtPassword.text.length < 8) {
            binding.edtPassword.error = "密码需要至少8位"
            return false
        } else if (binding.edtPassword.text.toString() != binding.edtPasswordConfirm.text.toString()) {
            binding.edtPasswordConfirm.error = "两次密码不一致"
            return false
        } else if (viewModel.dateInfo.get().date.isNullOrEmpty()) {
            "请选择出生日期".toast()
            return false
        } else if (viewModel.dateInfo.get().old < 18) {
            activity.dialogMsgShow("听说...你还没有成年...? 你懂的...", "关闭", null)
            return false
        }
        return true
    }
}
