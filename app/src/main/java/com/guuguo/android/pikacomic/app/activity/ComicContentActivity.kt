package com.guuguo.android.pikacomic.app.activity

import android.databinding.DataBindingUtil
import com.flyco.systembar.SystemBarHelper
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.viewModel.ComicContentViewModel
import com.guuguo.android.pikacomic.base.BaseActivity
import com.guuguo.android.pikacomic.databinding.ActivityComicContentBinding

class ComicContentActivity : BaseActivity() {
    lateinit var binding: ActivityComicContentBinding
    val viewModel = ComicContentViewModel(this)

    override fun getLayoutResId() = R.layout.activity_comic_content
    override fun getHeaderTitle() = "登录"
    override fun isNavigationBack() = false

    override fun setLayoutResId(layoutResId: Int) {
        binding = DataBindingUtil.setContentView(activity, layoutResId)
        binding.viewModel = viewModel
    }

    override fun initStatusBar() {
        SystemBarHelper.immersiveStatusBar(activity, 0f)
        SystemBarHelper.setStatusBarDarkMode(activity)
    }

    override fun initView() {
    }

    override fun loadData() {
        super.loadData()
//        viewModel.getCategory()
    }
}
