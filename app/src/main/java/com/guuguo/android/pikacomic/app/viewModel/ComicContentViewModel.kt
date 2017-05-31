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
import com.guuguo.android.pikacomic.entity.ImageEntity
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicContentViewModel(val activity: ComicContentActivity) : BaseObservable() {
    fun onBackClick(v: View) {
        activity.onBackPressed()
    }

    fun setReadStatus(ep: Int) {
        activity.comic.readEp = ep
        activity.comic.lastReadTime = Date()
        UOrm.db().save(activity.comic)
    }

    fun getContent(id: String, ep: Int, page: Int) {
        MyApiServer.getComicsContent(id, ep, page).subscribe(object : BaseCallback<ResponseModel<ComicsContentResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<ComicsContentResponse>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                t.data?.pages?.let {
                    activity.setContent(t.data!!.pages!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                activity.dialogErrorShow(msg, null)
            }
        })
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
                }
            }
        }
    }
    val scrollShowBarListener = object : RecyclerView.OnItemTouchListener {
        var downX = 0f
        var downY = 0f
        var isFirstClick = false
        override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
        }

        override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent): Boolean {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = e.rawX
                    downY = e.rawY
                }
                MotionEvent.ACTION_UP -> {
                    if (!isFirstClick && downX != 0f && downY != 0f && Math.abs(e.rawX - downX) < 3 && Math.abs(e.rawY - downY) < 3) {
                        if (activity.hideBar())
                            isFirstClick = false
                        else {
                            Completable.complete().delay(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
                                if (isFirstClick)
                                    activity.showBar()
                                else {
                                    activity.scaleRecycler(e.rawX, e.rawY)
                                }
                                isFirstClick = false
                            }
                            isFirstClick = !isFirstClick
                        }
                    } else if (isFirstClick) {
                        isFirstClick = !isFirstClick
                    }


                }
                MotionEvent.ACTION_MOVE -> {
                    if (downX != 0f && downY != 0f && (Math.abs(e.rawX - downX) >= 3 || Math.abs(e.rawY - downY) >= 3)) {
                        activity.hideBar()
                        downX = 0f
                        downY = 0f
                    }
                }
            }
            return false
        }


        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        }
    }
}

