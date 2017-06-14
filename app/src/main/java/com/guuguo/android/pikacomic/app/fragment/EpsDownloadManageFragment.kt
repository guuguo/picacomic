package com.guuguo.android.pikacomic.app.fragment

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.lib.app.LNBaseActivity
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.activity.BaseTitleFragmentActivity
import com.guuguo.android.pikacomic.app.activity.ComicContentActivity
import com.guuguo.android.pikacomic.app.adapter.EpDownloadAdapter
import com.guuguo.android.pikacomic.app.viewModel.EpsDownloadManageViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.constant.BUS_ACTION_URL_DOWNLOAD
import com.guuguo.android.pikacomic.databinding.FragmentEpDownloadManegeBinding
import com.guuguo.android.pikacomic.entity.ComicsEntity
import com.guuguo.android.pikacomic.entity.EpEntity
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class EpsDownloadManageFragment : BaseFragment() {
    lateinit var binding: FragmentEpDownloadManegeBinding
    val viewModel by lazy { EpsDownloadManageViewModel(this) }
    val epsAdapter = EpDownloadAdapter()
    lateinit var comicEntity: ComicsEntity

    override fun getLayoutResId() = R.layout.fragment_ep_download_manege
    override fun getHeaderTitle(): String {
        return comicEntity.title
    }

    companion object {
        val ARG_COMIC = "ARG_COMIC"
        val EPS_DOWNLOAD_MANAGE_FRAGMENT = 0x53

        fun intentTo(activity: Activity, comic: ComicsEntity) {
            val intent = Intent(activity, BaseTitleFragmentActivity::class.java)
            intent.putExtra(LNBaseActivity.SIMPLE_ACTIVITY_INFO, EpsDownloadManageFragment::class.java)

            val bundle = Bundle()
            bundle.putSerializable(ARG_COMIC, comic)

            intent.putExtras(bundle)
            activity.startActivityForResult(intent, EPS_DOWNLOAD_MANAGE_FRAGMENT)
        }
    }

    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }


    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        comicEntity = arguments.getSerializable(ARG_COMIC) as ComicsEntity
    }

    override fun loadData() {
        super.loadData()
        viewModel.getDownloadEps(comicEntity._id)
    }

    override fun initView() {
        super.initView()
        RxBus.get().register(this)
        binding.recycler.layoutManager = LinearLayoutManager(activity)
        epsAdapter.bindToRecyclerView(binding.recycler)
        epsAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.rtv_read)
                ComicContentActivity.intentTo(activity, comicEntity, epsAdapter.data[position].order)
        }
    }


    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = arrayOf(Tag(BUS_ACTION_URL_DOWNLOAD))
    )
    fun downloadNotify(epEntity: EpEntity) {
        if (epEntity.comicId == comicEntity._id) {
            val index = epsAdapter.data.indexOfFirst { it.order == epEntity.order }
            epsAdapter.data[index].downloadCount = epEntity.downloadCount
            epsAdapter.notifyItemChanged(index)
        }
    }

    override fun onDestroyView() {
        RxBus.get().unregister(this)
        super.onDestroyView()
    }

    fun setUpDownload(comics: ArrayList<EpEntity>?) {
        epsAdapter.setNewData(comics)
    }
}