package com.guuguo.android.pikacomic.app.activity

import android.app.Activity
import android.content.Intent
import com.flyco.systembar.SystemBarHelper
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.base.BaseActivity
import com.guuguo.android.pikacomic.databinding.ActivityComicContentBinding
import com.guuguo.android.pikacomic.entity.ComicsEntity


class PhotoViewActivity : BaseActivity() {
    lateinit var binding: ActivityComicContentBinding
    //argument
    var ep = 1
    lateinit var comic: ComicsEntity
    override fun getLayoutResId() = R.layout.activity_photo

    override val isFullScreen: Boolean
        get() = true

    override fun initStatusBar() {
        SystemBarHelper.setHeightAndPadding(activity, binding.llTopBar)
    }

    companion object {
        val ACTIVITY_COMIC_CONTENT = 0x12
        val ARG_COMIC = "ARG_COMIC"
        val ARG_EP = "ARG_EP"
        fun intentTo(context: Activity, comic: ComicsEntity, ep: Int) {
            val intent = Intent(context, ComicContentActivity::class.java)
            intent.putExtra(ARG_COMIC, comic)
            intent.putExtra(ARG_EP, ep)
            context.startActivityForResult(intent, ACTIVITY_COMIC_CONTENT)
        }
    }

}
