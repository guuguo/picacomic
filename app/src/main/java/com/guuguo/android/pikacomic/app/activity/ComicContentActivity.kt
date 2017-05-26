package com.guuguo.android.pikacomic.app.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.flyco.systembar.SystemBarHelper
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.adapter.ComicContentAdapter
import com.guuguo.android.pikacomic.app.viewModel.ComicContentViewModel
import com.guuguo.android.pikacomic.base.BaseActivity
import com.guuguo.android.pikacomic.databinding.ActivityComicContentBinding
import com.guuguo.android.pikacomic.entity.ComicsContentResponse
import com.guuguo.android.pikacomic.entity.ComicsEntity

class ComicContentActivity : BaseActivity() {
    lateinit var binding: ActivityComicContentBinding
    val viewModel = ComicContentViewModel(this)
    val comicsContentAdapter = ComicContentAdapter()
    //argument
    var ep = 1
    lateinit var comic: ComicsEntity
    override fun getLayoutResId() = R.layout.activity_comic_content

    override fun setLayoutResId(layoutResId: Int) {
        binding = DataBindingUtil.setContentView(activity, layoutResId)
        binding.viewModel = viewModel
    }

    override fun initStatusBar() {
        SystemBarHelper.immersiveStatusBar(activity, 0f)
        SystemBarHelper.setStatusBarDarkMode(activity)
    }

    companion object {
        val ARG_COMIC = "ARG_COMIC"
        val ARG_EP = "ARG_EP"
        fun intentTo(context: Context, comic: ComicsEntity, ep: Int) {
            val intent = Intent(context, ComicContentActivity::class.java)
            intent.putExtra(ARG_COMIC, comic)
            intent.putExtra(ARG_EP, ep)
            context.startActivity(intent)
        }
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        comic = intent.getSerializableExtra(ARG_COMIC) as ComicsEntity
        ep = intent.getIntExtra(ARG_EP, 1)
    }

    override fun initView() {
        super.initView()
        binding.recycler.layoutManager = LinearLayoutManager(activity)
        comicsContentAdapter.bindToRecyclerView(binding.recycler)
        comicsContentAdapter.setOnLoadMoreListener({
            page++
            loadData()
        }, binding.recycler)

    }

    override fun loadData() {
        super.loadData()
        if (page == 1)
            activity.dialogLoadingShow("正在加载中")
        viewModel.getContent(comic._id, ep, page)
    }

    var page = 1
    fun setContent(data: ComicsContentResponse.PagesEntity) {
        if (data.pages <= data.page)
            comicsContentAdapter.loadMoreEnd()
        else {
            comicsContentAdapter.loadMoreComplete()
        }
        if (page == 1)
            comicsContentAdapter.setNewData(data.docs)
        else
            comicsContentAdapter.addData(data.docs)
    }
}
