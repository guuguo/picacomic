package com.guuguo.android.pikacomic.app.fragment

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.guuguo.android.lib.app.LNBaseActivity
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.activity.BaseTitleFragmentActivity
import com.guuguo.android.pikacomic.app.adapter.ComicsAdapter
import com.guuguo.android.pikacomic.app.fragment.ComicsFragment.Companion.ARG_GET_COMICS
import com.guuguo.android.pikacomic.app.viewModel.ComicDetailModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.constant.loadingPlaceHolder
import com.guuguo.android.pikacomic.databinding.FragmentComicDetailBinding
import com.guuguo.android.pikacomic.entity.ComicsEntity

/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicDetailFragment : BaseFragment() {
    lateinit var binding: FragmentComicDetailBinding
    val viewModel by lazy { ComicDetailModel(this) }
    val comicsAdapter = ComicsAdapter()

    override fun getLayoutResId() = R.layout.fragment_comic_detail
    var getComicsType = 0
    lateinit var comicEntity: ComicsEntity

    override fun getHeaderTitle(): String {
        return comicEntity.title
    }

    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }


    companion object {
        val ARG_COMIC = "ARG_COMIC"

        fun intentTo(activity: Activity, comic: ComicsEntity) {
            val intent = Intent(activity, BaseTitleFragmentActivity::class.java)
            intent.putExtra(LNBaseActivity.SIMPLE_ACTIVITY_INFO, ComicDetailFragment::class.java)

            val bundle = Bundle()
            bundle.putSerializable(ARG_COMIC, comic)

            intent.putExtras(bundle)
            activity.startActivity(intent)
        }
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)
        getComicsType = arguments.getInt(ARG_GET_COMICS)
        comicEntity = arguments.getSerializable(ARG_COMIC) as ComicsEntity

    }

    override fun initView() {
        super.initView()
        binding.rtvRead.setOnClickListener {

        }
        setUpComic(comicEntity)
    }


    override fun loadData() {
        super.loadData()
        viewModel.getComic(comicEntity._id)
    }

    fun setUpComic(comic: ComicsEntity) {
        val colorPrimary = ContextCompat.getColor(activity, R.color.colorPrimary)
        val colorGray = ContextCompat.getColor(activity, R.color.black40)

        binding.tvTitle.text = comic.title
        binding.tvContent.text = comic.description
        Glide.with(activity).load(comic.thumb?.getOriginUrl()).asBitmap().placeholder(loadingPlaceHolder).centerCrop().into(binding.ivBanner)
        if (comic.isIsLiked) {
            binding.tvLike.setTextColor(colorPrimary)
            DrawableCompat.setTint(binding.ivLike.drawable, colorPrimary)
        } else {
            binding.tvLike.setTextColor(colorGray)
            DrawableCompat.setTint(binding.ivLike.drawable, colorGray)
        }
        if (comic.isIsFavourite) {
            DrawableCompat.setTint(binding.ivCollect.drawable, colorPrimary)
            binding.tvCollect.setTextColor(colorPrimary)
            binding.tvCollect.text = "已收藏"
        } else {
            DrawableCompat.setTint(binding.ivCollect.drawable, colorGray)
            binding.tvCollect.setTextColor(colorGray)
            binding.tvCollect.text = "收藏"
        }
        binding.tvLike.text = comic.likesCount.toString()
    }
}