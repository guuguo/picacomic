package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import android.graphics.Color
import android.os.Build
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.FrameLayout
import com.flyco.systembar.SystemBarHelper
import com.guuguo.android.pikacomic.app.activity.ComicContentActivity
import com.guuguo.android.pikacomic.db.UOrm
import com.guuguo.android.pikacomic.entity.ComicsContentResponse
import com.guuguo.android.pikacomic.entity.EpPagesEntity
import com.guuguo.android.pikacomic.entity.ImageEntity
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicContentViewModel(val activity: ComicContentActivity) : BaseObservable() {
    fun onBackClick(v: View) {
        activity.onBackPressed()
    }

    fun setReadStatus(ep: Int, position: Int) {
        activity.comic.readEp = ep
        activity.comic.lastReadTime = System.currentTimeMillis()
        activity.comic.readPosition = position
        UOrm.db().single().save(activity.comic)
    }

    fun getContentFromNet(id: String, ep: Int, page: Int) {
        if (activity.firstLoad) {
            activity.dialogLoadingShow("正在加载中")
        }
        MyApiServer.getComicsContent(id, ep, page).subscribe(object : BaseCallback<ResponseModel<ComicsContentResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<ComicsContentResponse>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                t.data?.pages?.let {
                    //保存epPageEntity
                    t.data?.pages?.comicId = activity.comic._id
                    t.data?.pages?.ep = activity.ep
                    UOrm.db().save(t.data!!.pages)

                    //保存epEntity
                    t.data!!.ep?.save(ep, id)
                    activity.setContent(t.data!!.pages!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                activity.dialogErrorShow(msg, null)
            }
        })
    }

    fun getContent(id: String, ep: Int, page: Int) {
        val pageEntity = EpPagesEntity.query(id, ep, page)
        if (pageEntity != null) {
            activity.setContent(pageEntity)
            if (activity.lastReadEp == ep)
                if (activity.lastReadPosition > activity.comicsContentAdapter.data.size) {
                    activity.page++
                    getContent(id, ep, activity.page)
                } else {
                    activity.toPosition(activity.lastReadPosition - 1)
                }
        } else {
            getContentFromNet(id, ep, page)
            activity.firstLoad = false
        }
    }

    fun unImmersiveStatusBar(window: Window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT

            var systemUiVisibility = window.decorView.systemUiVisibility
            systemUiVisibility = systemUiVisibility and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv()
            systemUiVisibility = systemUiVisibility and View.SYSTEM_UI_FLAG_LAYOUT_STABLE.inv()
            window.decorView.systemUiVisibility = systemUiVisibility
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        val contentView = window.decorView.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        val rootView = contentView.getChildAt(0)
        val statusBarHeight = SystemBarHelper.getStatusBarHeight(window.context)
        if (rootView != null) {
            val lp = rootView.layoutParams as FrameLayout.LayoutParams
            ViewCompat.setFitsSystemWindows(rootView, false)
            lp.topMargin = -statusBarHeight
            rootView.layoutParams = lp
        }
    }

    val scrollReadInfoChangeListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val layoutManager = recyclerView!!.layoutManager
            if (layoutManager is LinearLayoutManager) {
                val linearManager = layoutManager
                val firstItemPosition = linearManager.findFirstVisibleItemPosition()
                val item = activity.comicsContentAdapter.getItem(firstItemPosition)
                if (item is ImageEntity) {
                    activity.setUpReadInfo(item.ep, item.position, item.total)
                    setReadStatus(item.ep, item.position)
                }
            }
        }
    }


    val onItemTouchListener = object : RecyclerView.OnItemTouchListener {
        override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
        }

        override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent): Boolean {
            when (e.action) {
                MotionEvent.ACTION_UP -> activity.fingerUp()
            }
            activity.mDetector.onTouchEvent(e);
            return false
        }


        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        }
    }
}

