package com.guuguo.android.pikacomic.app.fragment

import android.databinding.DataBindingUtil
import android.graphics.BitmapFactory
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.adapter.AnnouncementsCardAdapter
import com.guuguo.android.pikacomic.app.adapter.ComicsAdapter
import com.guuguo.android.pikacomic.app.viewModel.HomeViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.databinding.FragmentHomeBinding
import com.guuguo.android.pikacomic.entity.AnnouncementsEntity
import com.guuguo.android.pikacomic.entity.AnnouncementsResponse
import com.guuguo.android.pikacomic.entity.ComicsEntity
import com.guuguo.android.pikacomic.utils.BlurBitmapUtils
import com.guuguo.android.pikacomic.utils.ViewSwitchUtils
import com.view.jameson.library.CardScaleHelper


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding
    val viewModel by lazy { HomeViewModel(this) }
    val announcementsCardAdapter = AnnouncementsCardAdapter()
    val comicsAdapter = ComicsAdapter()

    override fun getLayoutResId() = R.layout.fragment_home

    val mCardScaleHelper = CardScaleHelper()
    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun initView() {
        super.initView()
        initAnnouncement()
        initCategory()
        binding.ibRefresh.setOnClickListener {
            viewModel.getComicRandomFromNet()
        }
        comicsAdapter.setOnItemClickListener { _, _, position ->
            ComicDetailFragment.intentTo(activity, comicsAdapter.getItem(position))
        }
    }

    private fun initCategory() {
        binding.llComics.setAdapter(comicsAdapter)
    }

    private fun initAnnouncement() {
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        announcementsCardAdapter.bindToRecyclerView(binding.recyclerView)
        // mRecyclerView绑定scale效果
        mCardScaleHelper.attachToRecyclerView(binding.recyclerView)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.blur_announce)
        ViewSwitchUtils.startSwitchBackgroundAnim(binding.ivBlurView, BlurBitmapUtils.getBlurBitmap(activity, bitmap, 15))
    }

    var mList: List<AnnouncementsEntity> = arrayListOf()
    fun setUpAnnouncementsBanner(response: AnnouncementsResponse.Response) {
        mList = response.docs!!
        announcementsCardAdapter.setNewData(response.docs)
    }


    override fun loadData() {
        super.loadData()
        viewModel.getAnnouncements()
        viewModel.getComicsRandom()
    }

    fun setUpComics(comics: List<ComicsEntity>) {
        comics.forEach { it.save() }
        comicsAdapter.setNewData(comics)
    }

}