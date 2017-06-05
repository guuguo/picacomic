package com.guuguo.android.pikacomic.app.fragment

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.adapter.ComicsAdapter
import com.guuguo.android.pikacomic.app.viewModel.ComicDownloadManageViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.databinding.FragmentComicDownloadManegeBinding
import com.guuguo.android.pikacomic.entity.ComicsEntity
import kotlinx.android.synthetic.main.layout_title_bar.*

/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicDownloadManageFragment : BaseFragment() {
    lateinit var binding: FragmentComicDownloadManegeBinding
    val viewModel by lazy { ComicDownloadManageViewModel(this) }
    val comicsAdapter = ComicsAdapter()

    override fun getLayoutResId() = R.layout.fragment_comic_download_manege
    override fun getHeaderTitle(): String {
        return "下载管理"
    }

    override fun setTitle(title: String) {
        tv_title_bar.text = title
    }

    override fun initToolbar() {
        if (getMenuResId() != 0) {
            id_toolbar.inflateMenu(getMenuResId())
            id_toolbar.setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
        }
        setTitle(getHeaderTitle())
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

    fun  setUpDownload(comics: ArrayList<ComicsEntity>?) {}
}