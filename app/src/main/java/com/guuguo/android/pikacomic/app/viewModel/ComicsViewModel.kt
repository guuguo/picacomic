package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import com.guuguo.android.pikacomic.app.fragment.ComicsFragment
import com.guuguo.android.pikacomic.entity.ComicsRandomResponse
import com.guuguo.android.pikacomic.entity.ComicsResponse
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicsViewModel(val fragment: ComicsFragment) : BaseObservable() {
    val activity = fragment.activity
    val TYPE_CATEGORY = 0
    val TYPE_MY_FAVORITE = 1
    fun getComics(page: Int, category: String? = null, type: Int = TYPE_CATEGORY) {
        if (page == 1) {
            activity.dialogLoadingShow("正在加载漫画列表")
        }
        val sub = when (type) {
            TYPE_CATEGORY -> MyApiServer.getComics(page, category)
            TYPE_MY_FAVORITE -> MyApiServer.getMyFavorite(page)
            else -> MyApiServer.getComics(page, category)
        }
        sub.subscribe(object : BaseCallback<ResponseModel<ComicsResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<ComicsResponse>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                t.data?.comics?.let {
                    fragment.setUpComicsPage(t.data!!.comics!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                activity.dialogErrorShow(msg, null)
            }
        })
    }

    fun getComicsRank() {
        activity.dialogLoadingShow("正在加载漫画列表")
        MyApiServer.getComicsRank().subscribe(object : BaseCallback<ResponseModel<ComicsRandomResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<ComicsRandomResponse>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                t.data?.comics?.let {
                    fragment.setUpComics(t.data!!.comics!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                activity.dialogErrorShow(msg, null)
            }
        })
    }
}