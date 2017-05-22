package com.guuguo.android.pikacomic.app.fragment

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.databinding.FragmentGameBinding

/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class GameFragment : BaseFragment() {
    lateinit var binding: FragmentGameBinding

    override fun getLayoutResId() = R.layout.fragment_game


    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        return binding.root
    }

}