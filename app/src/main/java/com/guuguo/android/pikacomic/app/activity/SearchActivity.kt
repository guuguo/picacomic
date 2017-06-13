package com.guuguo.android.pikacomic.app.activity

import am.project.support.utils.InputMethodUtils
import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.design.widget.AppBarLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.florent37.viewanimator.ViewAnimator
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.adapter.ComicsAdapter
import com.guuguo.android.pikacomic.app.fragment.ComicDetailFragment
import com.guuguo.android.pikacomic.app.viewModel.SearchViewModel
import com.guuguo.android.pikacomic.base.BaseActivity
import com.guuguo.android.pikacomic.databinding.ActivitySearchBinding
import com.guuguo.android.pikacomic.db.UOrm
import com.guuguo.android.pikacomic.entity.ComicsEntity
import com.guuguo.android.pikacomic.entity.ComicsResponse
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {
    lateinit var binding: ActivitySearchBinding
    val viewModel = SearchViewModel(this)
    val comicsAdapter = ComicsAdapter()

    override fun getLayoutResId() = R.layout.activity_search
    override fun isNavigationBack() = true
    override fun getToolBar(): Toolbar? {
        return id_toolbar
    }

    override fun setLayoutResId(layoutResId: Int) {
        binding = DataBindingUtil.setContentView(activity, layoutResId)
        binding.viewModel = viewModel
    }

    override fun initToolBar() {
        super.initToolBar()
        getToolBar()?.navigationIcon = ContextCompat.getDrawable(activity, R.drawable.ic_arrowleft)
        supportActionBar?.title = ""
    }

    override fun getAppBar(): AppBarLayout? {
        return appbar
    }

    companion object {
        val ARG_TRANSACTION_VIEW = "search_view"
        fun intentTo(context: Activity, view: View) {
            val intent = Intent(context, SearchActivity::class.java)
            ActivityCompat.startActivity(context, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(context, view, ARG_TRANSACTION_VIEW).toBundle())
        }
    }

    override fun onBackPressedSupport() {
        ActivityCompat.finishAfterTransition(activity)
    }

    fun onSearchClearClick(v: View) {
        binding.edtSearch.setText("")
    }

    override fun loadData() {
        super.loadData()
        viewModel.getHotKeys()
    }

    override fun initView() {
        super.initView()
        binding.recycler.layoutManager = GridLayoutManager(activity, 3)
        comicsAdapter.bindToRecyclerView(binding.recycler)
        comicsAdapter.setOnLoadMoreListener({
            page++
            viewModel.getComics(page, binding.edtSearch.text.toString())
        }, binding.recycler)
        comicsAdapter.setOnItemClickListener { _, _, position ->
            ComicDetailFragment.intentTo(activity, comicsAdapter.getItem(position)!!)
        }
        comicsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)

        binding.recycler.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_DOWN) {
                    focusRecycler()
                }
                return super.onInterceptTouchEvent(rv, e)
            }
        })
        binding.edtSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchNow()
                true
            } else false
        }
        binding.edtSearch.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                showTags()
            else
                hideTags()
        }
    }

    fun setUpKeywords(keywords: List<String>) {
        if (comicsAdapter.data.isEmpty()) {
            binding.wlTags.removeAllViews()

            keywords.forEach {
                val view = layoutInflater.inflate(R.layout.item_tag, binding.wlTags, false)
                val tvContent = view.findViewById(R.id.tv_content) as TextView
                tvContent.text = it
                val str = it
                binding.wlTags.addView(view)

                view.isClickable = true
                view.setOnClickListener {
                    binding.edtSearch.setText(str)
                    searchNow()
                }
            }
            showTags()
        }
    }

    private fun showTags() {
        if (binding.wlTags.visibility != View.VISIBLE) {
            ViewAnimator.animate(binding.wlTags).alpha(0f, 1f).duration(200).start()
            binding.wlTags.visibility = View.VISIBLE
        }
    }

    private fun hideTags() {
        if (binding.wlTags.visibility != View.GONE) {
            ViewAnimator.animate(binding.wlTags).alpha(1f, 0f).onStop { binding.wlTags.visibility = View.GONE }.duration(200).start()
        }
    }

    private fun searchNow() {
        page = 1
        val str = binding.edtSearch.text.toString()
        if (str.isEmpty())
            setUpComics(arrayListOf())
        else
            viewModel.getComics(page, str)
    }

    var page = 1;
    fun setUpComicsPage(comics: ComicsResponse.ComicsBean) {
        focusRecycler()

        setUpComics(comics.docs)
        if (comics.pages <= comics.page)
            comicsAdapter.loadMoreEnd()
        else {
            comicsAdapter.loadMoreComplete()
            comicsAdapter.disableLoadMoreIfNotFullPage()
        }
    }

    private fun focusRecycler() {
        InputMethodUtils.closeInputMethod(binding.edtSearch,true)
        binding.recycler.requestFocus()
    }


    fun setUpComics(comics: List<ComicsEntity>) {
        if (comicsAdapter.emptyViewCount < 1)
            comicsAdapter.setEmptyView(R.layout.simple_empty_view)
        UOrm.db().insert(comics)
        if (page == 1)
            comicsAdapter.setNewData(comics)
        else
            comicsAdapter.addData(comics)
    }

}
