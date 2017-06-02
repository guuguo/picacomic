package com.guuguo.android.pikacomic.app.fragment

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.guuguo.android.lib.app.LNBaseActivity
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.activity.BaseTitleFragmentActivity
import com.guuguo.android.pikacomic.app.activity.ComicContentActivity
import com.guuguo.android.pikacomic.app.adapter.EpAdapter
import com.guuguo.android.pikacomic.app.fragment.ComicsFragment.Companion.ARG_GET_COMICS
import com.guuguo.android.pikacomic.app.viewModel.ComicDetailViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.databinding.FragmentComicDetailBinding
import com.guuguo.android.pikacomic.db.UOrm
import com.guuguo.android.pikacomic.entity.ComicsEntity
import kotlinx.android.synthetic.main.fragment_comic_detail.*
import zlc.season.rxdownload2.RxDownload

/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicDetailFragment : BaseFragment() {
    lateinit var binding: FragmentComicDetailBinding
    val viewModel by lazy { ComicDetailViewModel(this) }
    val epAdapter = EpAdapter()

    override fun getLayoutResId() = R.layout.fragment_comic_detail
    var getComicsType = 0
    lateinit var comicEntity: ComicsEntity

    override fun getHeaderTitle(): String {
        return comicEntity._id
    }

    override fun getMenuResId(): Int {
        return R.menu.download
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_download -> {
                viewModel.downLoadComic(1);
            }
        }
        return super.onOptionsItemSelected(item)
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

    private fun readDbComic(): ComicsEntity? {
        val tempComic = UOrm.db().queryById(comicEntity._id, ComicsEntity::class.java)
        return tempComic
    }

    override fun initView() {
        super.initView()
        val dbComic = readDbComic()
        if (dbComic != null)
            comicEntity = dbComic

        recycler_ep.setAdapter(epAdapter)
        epAdapter.setOnItemClickListener { _, _, i ->
            ComicContentActivity.intentTo(activity, viewModel.comic.get(), i + 1)
        }
        binding.rtvRead.setOnClickListener {
            ComicContentActivity.intentTo(activity, viewModel.comic.get(), if (epAdapter.readEp != 0) epAdapter.readEp else 1)
        }
    }


    override fun loadData() {
        super.loadData()
        viewModel.bindResult(comicEntity)
        viewModel.getComic(viewModel.comic.get()._id)
    }

    fun setUpComic(comic: ComicsEntity) {
        Glide.with(activity).load(comic.thumb?.getOriginUrl()).asBitmap().placeholder(R.drawable.placeholder_loading).centerCrop().into(binding.ivBanner)
        val array: ArrayList<String> = arrayListOf()
        (1..comic.epsCount).map { array.add(it.toString()) }
        epAdapter.readEp = comic.readEp.safe()
        epAdapter.setNewData(array)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ComicContentActivity.ACTIVITY_COMIC_CONTENT) {
            viewModel.bindResult(readDbComic()!!)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}