package com.guuguo.android.pikacomic.app.fragment

import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.adapter.ComicsAdapter
import com.guuguo.android.pikacomic.app.viewModel.MineViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.databinding.FragmentMineBinding
import com.guuguo.android.pikacomic.entity.ComicsEntity
import com.guuguo.android.pikacomic.entity.UserEntity


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class MineFragment : BaseFragment() {
    lateinit var binding: FragmentMineBinding
    val viewModel by lazy { MineViewModel(this) }
    val comicsAdapter = ComicsAdapter()

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

    override fun initView() {
        super.initView()
        comicsAdapter.setOnItemClickListener { _, _, position ->
            ComicDetailFragment.intentTo(activity, comicsAdapter.getItem(position))
        }

        val inf = activity.layoutInflater
        val view = inf.inflate(R.layout.layout_history_header, contentView as ViewGroup, false)
        comicsAdapter.addHeaderView(view)
        binding.recycler.layoutManager = GridLayoutManager(activity, 3)
        comicsAdapter.bindToRecyclerView(binding.recycler)
    }

    fun setUpMine(result: UserEntity) {
        Glide.with(activity).load(result.avatar?.getOriginUrl()).asBitmap().placeholder(R.drawable.placeholder_loading).error(R.drawable.placeholder_empty).centerCrop().into(binding.ivAvatar)
        if (result.isPunched) {
            binding.tvKnock.visibility = View.GONE
            binding.ivKnock.visibility = View.GONE
        } else {
            binding.tvKnock.visibility = View.VISIBLE
            binding.ivKnock.visibility = View.VISIBLE
        }
    }

    var page = 0
    val limit = 20
    override fun lazyLoad() {
        super.lazyLoad()
        page = 0
        comicsAdapter.data.clear()
        comicsAdapter.notifyDataSetChanged()
        loadHistory()
    }

    fun loadHistory() {
        viewModel.getHistoryComics(page, limit)
    }

    fun setUpHistory(result: ArrayList<ComicsEntity>) {
        if (comicsAdapter.emptyViewCount < 1)
            comicsAdapter.setEmptyView(R.layout.simple_empty_view)
        comicsAdapter.addData(result)
    }

}