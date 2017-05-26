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
    fun getComics(page: Int, category: String? = null) {
        activity.dialogLoadingShow("正在加载漫画列表")
        MyApiServer.getComics(page, category).subscribe(object : BaseCallback<ResponseModel<ComicsResponse>>() {
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