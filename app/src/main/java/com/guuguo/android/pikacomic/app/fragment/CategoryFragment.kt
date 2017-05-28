package com.guuguo.android.pikacomic.app.fragment

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.adapter.CategoryAdapter
import com.guuguo.android.pikacomic.app.viewModel.CategoryViewModel
import com.guuguo.android.pikacomic.base.BaseFragment
import com.guuguo.android.pikacomic.databinding.FragmentCategoryBinding
import com.guuguo.android.pikacomic.entity.CategoryEntity
import kotlinx.android.synthetic.main.layout_title_bar.*

/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class CategoryFragment : BaseFragment() {
    lateinit var binding: FragmentCategoryBinding
    val viewModel by lazy { CategoryViewModel(this) }
    val categoryAdapter = CategoryAdapter()

    override fun getLayoutResId() = R.layout.fragment_category

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_refresh -> {
                viewModel.getCategoryFromNet()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun initToolbar() {
        if (getMenuResId() != 0) {
            id_toolbar.inflateMenu(getMenuResId())
            id_toolbar.setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
        }
        setTitle(getHeaderTitle())
    }

    override fun setTitle(title: String) {
        tv_title_bar.text = title
    }

    override fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun getMenuResId() = R.menu.refresh
    override fun getHeaderTitle(): String {
        return "漫画分类"
    }

    override fun initView() {
        super.initView()
        binding.llCategory.setAdapter(categoryAdapter)
        categoryAdapter.setOnItemClickListener { _, _, i ->
            ComicsFragment.intentTo(activity, ComicsFragment.TYPE_COMICS_CATEGORY, categoryAdapter.getItem(i))
        }
        binding.tvRecently.setOnClickListener {
            ComicsFragment.intentTo(activity, ComicsFragment.TYPE_COMICS_RECENTLY)
        }
        binding.tvRank.setOnClickListener {
            ComicsFragment.intentTo(activity, ComicsFragment.TYPE_COMICS_RANK)
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.getCategory()
    }

    fun setUpCategory(categories: List<CategoryEntity>) {
        categoryAdapter.setNewData(categories)
    }
}