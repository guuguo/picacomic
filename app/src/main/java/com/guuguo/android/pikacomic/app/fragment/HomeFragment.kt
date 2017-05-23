package com.guuguo.android.pikacomic.app.fragment

import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.adapter.AnnouncementsCardAdapter
import com.guuguo.android.pikacomic.app.viewModel.HomeViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.databinding.FragmentHomeBinding
import com.guuguo.android.pikacomic.entity.AnnouncementsResponse
import com.view.jameson.library.CardScaleHelper


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding
    val viewModel by lazy { HomeViewModel(this) }
    val announcementsCardAdapter = AnnouncementsCardAdapter()
    override fun getLayoutResId() = R.layout.fragment_home

    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun initView() {
        super.initView()
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.setLayoutManager(linearLayoutManager)
        announcementsCardAdapter.bindToRecyclerView(binding.recyclerView)
        // mRecyclerView绑定scale效果
        CardScaleHelper().attachToRecyclerView(binding.recyclerView)
    }

    fun setUpAnnouncementsBanner(response: AnnouncementsResponse.Response) {
        announcementsCardAdapter.setNewData(response.docs)
    }

    override fun loadData() {
        super.loadData()
        viewModel.getAnnouncements()
    }
}