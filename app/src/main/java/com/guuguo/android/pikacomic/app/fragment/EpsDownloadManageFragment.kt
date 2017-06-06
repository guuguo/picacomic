package com.guuguo.android.pikacomic.app.fragment

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.lib.app.LNBaseActivity
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.activity.BaseTitleFragmentActivity
import com.guuguo.android.pikacomic.app.adapter.ComicsAdapter
import com.guuguo.android.pikacomic.app.adapter.EpAdapter
import com.guuguo.android.pikacomic.app.viewModel.ComicsDownloadManageViewModel
import com.guuguo.android.pikacomic.app.viewModel.EpsDownloadManageViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.databinding.FragmentComicDownloadManegeBinding
import com.guuguo.android.pikacomic.databinding.FragmentEpDownloadManegeBinding
import com.guuguo.android.pikacomic.entity.ComicsEntity
import kotlinx.android.synthetic.main.layout_title_bar.*

/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class EpsDownloadManageFragment : BaseFragment() {
    lateinit var binding: FragmentEpDownloadManegeBinding
    val viewModel by lazy { EpsDownloadManageViewModel(this) }
    val comicsAdapter = ComicsAdapter()

    override fun getLayoutResId() = R.layout.fragment_ep_download_manege
    override fun getHeaderTitle(): String {
        return "下载管理"
    }

    companion object {
        val COMIC_DOWNLOAD_MANAGE_FRAGMENT = 0x9
        fun intentTo(activity: Activity) {
            val intent = Intent(activity, BaseTitleFragmentActivity::class.java)
            intent.putExtra(LNBaseActivity.SIMPLE_ACTIVITY_INFO, EpsDownloadManageFragment::class.java)

            val bundle = Bundle()

            intent.putExtras(bundle)
            activity.startActivityForResult(intent, COMIC_DOWNLOAD_MANAGE_FRAGMENT)
        }
    }

    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun loadData() {
        super.loadData()
        viewModel.getDownloadComics()
    }

    override fun initView() {
        super.initView()
        binding.recycler.layoutManager = GridLayoutManager(activity, 3)
        comicsAdapter.bindToRecyclerView(binding.recycler)
    }

    fun setUpDownload(comics: EpAdapter<ComicsEntity>?) {
        comicsAdapter.setNewData(comics)
    }
}