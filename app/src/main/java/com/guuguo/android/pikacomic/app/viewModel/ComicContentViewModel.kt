package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import com.guuguo.android.pikacomic.app.activity.ComicContentActivity
import com.guuguo.android.pikacomic.entity.ComicsContentResponse
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicContentViewModel(val activity: ComicContentActivity) : BaseObservable() {
    fun getContent(id:String,ep:Int,page:Int) {
        MyApiServer.getComicsContent(id,ep,page).subscribe(object : BaseCallback<ResponseModel<ComicsContentResponse>>() {
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
}