package com.guuguo.android.pikacomic.app.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.design.widget.AppBarLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.View
import com.flyco.systembar.SystemBarHelper
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.adapter.ComicContentAdapter
import com.guuguo.android.pikacomic.app.viewModel.SearchViewModel
import com.guuguo.android.pikacomic.base.BaseActivity
import com.guuguo.android.pikacomic.databinding.ActivitySearchBinding
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {
    lateinit var binding: ActivitySearchBinding
    val viewModel = SearchViewModel(this)
    val comicsContentAdapter = ComicContentAdapter()
    
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
        val ARG_TRANSACTION_VIEW="search_view"
        fun intentTo(context: Activity, view: View) {
            val intent = Intent(context, ComicContentActivity::class.java)
            ActivityCompat.startActivity(context,intent, ActivityOptionsCompat.makeSceneTransitionAnimation(context,view,ARG_TRANSACTION_VIEW).toBundle())
        }
    }


    override fun initView() {
        super.initView()
        SystemBarHelper.tintStatusBar(activity, ContextCompat.getColor(activity, R.color.colorPrimary), 0f)
    }

}
