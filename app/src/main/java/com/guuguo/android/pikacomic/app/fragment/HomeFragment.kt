package com.guuguo.android.pikacomic.app.fragment

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.databinding.ActivityLoginBinding
import com.guuguo.android.pikacomic.databinding.FragmentHomeBinding

/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding

    override fun getLayoutResId()= R.layout.fragment_home


    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
    }
    
}