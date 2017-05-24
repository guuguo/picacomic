package com.guuguo.android.pikacomic.app.fragment

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.viewModel.ComicsViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.databinding.FragmentComicsBinding
import com.guuguo.android.pikacomic.entity.CategoryEntity

/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicsFragment : BaseFragment() {
    lateinit var binding: FragmentComicsBinding
    val viewModel by lazy { ComicsViewModel(this) }

    override fun getLayoutResId() = R.layout.fragment_comics

    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

  
}