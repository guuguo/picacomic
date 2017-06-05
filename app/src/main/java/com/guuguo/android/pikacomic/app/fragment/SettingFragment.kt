package com.guuguo.android.pikacomic.app.fragment
import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.viewModel.SettingViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.databinding.FragmentSettingBinding
import kotlinx.android.synthetic.main.layout_title_bar.*
/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class SettingFragment : BaseFragment() {
    lateinit var binding: FragmentSettingBinding
    val viewModel by lazy { SettingViewModel(this) }
    override fun getLayoutResId() = R.layout.fragment_setting
    override fun getHeaderTitle(): String {
        return "设置"
    }
    override fun setTitle(title: String) {
        tv_title_bar.text = title
    }
    override fun initToolbar() {
        if (getMenuResId() != 0) {
            id_toolbar.inflateMenu(getMenuResId())
            id_toolbar.setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
        }
        setTitle(getHeaderTitle())
    }
    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }
    override fun initView() {
        super.initView()
        binding.sSwitch.setOnClickListener {
            if (binding.sSwitch.isChecked)
                PatternLockFragment.intentTo(activity, PatternLockFragment.TYPE_CREATE_PATTERN)
            else
                PatternLockFragment.intentTo(activity, PatternLockFragment.TYPE_CANCEL_PATTERN)
        }
        binding.sSwitch.isChecked = !LocalData.patternStr.isNullOrEmpty()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PatternLockFragment.PATTERN_LOCK_FRAGMENT ->
                if (resultCode == Activity.RESULT_OK && !binding.sSwitch.isChecked) {
                    LocalData.patternStr = ""
                    binding.sSwitch.isChecked = false
                } else if (resultCode == Activity.RESULT_OK) {
                    binding.sSwitch.isChecked = true
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    binding.sSwitch.isChecked = !binding.sSwitch.isChecked
                }
        }
    }
}