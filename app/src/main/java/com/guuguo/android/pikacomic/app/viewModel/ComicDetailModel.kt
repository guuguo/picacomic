package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import com.guuguo.android.pikacomic.app.fragment.ComicDetailFragment
import com.guuguo.android.pikacomic.entity.ComicDetailResponse
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicDetailModel(val fragment: ComicDetailFragment) : BaseObservable() {
    val activity = fragment.activity
    fun getComic(id: String) {
        MyApiServer.getComicDetail(id).subscribe(object : BaseCallback<ResponseModel<ComicDetailResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<ComicDetailResponse>) {
                super.onSuccess(t)
                t.data?.comic?.let {
                    fragment.setUpComic(t.data!!.comic!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                activity.dialogErrorShow(msg, null)
            }
        })
    }
}