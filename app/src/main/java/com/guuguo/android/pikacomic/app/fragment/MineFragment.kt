package com.guuguo.android.pikacomic.app.fragment

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.viewModel.MineViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.constant.loadingPlaceHolder
import com.guuguo.android.pikacomic.databinding.FragmentMineBinding
import com.guuguo.android.pikacomic.entity.UserEntity

/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class MineFragment : BaseFragment() {
    lateinit var binding: FragmentMineBinding
    val viewModel by lazy { MineViewModel(this) }

    override fun getLayoutResId() = R.layout.fragment_mine


    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun loadData() {
        super.loadData()
        viewModel.getUserProfile()
    }

    fun setUpMine(result: UserEntity) {
        Glide.with(activity).load(result.avatar?.getOriginUrl()).asBitmap().placeholder(loadingPlaceHolder).centerCrop().into(binding.ivAvatar)
        if (result.isPunched) {
            binding.tvKnock.visibility = View.GONE
            binding.ivKnock.visibility = View.GONE
        } else {
            binding.tvKnock.visibility = View.VISIBLE
            binding.ivKnock.visibility = View.VISIBLE
        }
    }

}