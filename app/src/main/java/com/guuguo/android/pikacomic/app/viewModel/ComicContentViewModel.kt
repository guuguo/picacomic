package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import com.guuguo.android.pikacomic.app.activity.ComicContentActivity


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicContentViewModel(val activity: ComicContentActivity) : BaseObservable() {
    fun getContent() {
        activity.dialogLoadingShow("正在加载中")
//        MyApiServer.getCategoryFromNet().subscribe(object : BaseCallback<ResponseModel<CategoryResponse>>() {
//            override fun onSubscribe(d: Disposable?) {
//                activity.addApiCall(d)
//            }
//
//            override fun onSuccess(t: ResponseModel<CategoryResponse>) {
//                super.onSuccess(t)
//                activity.dialogDismiss()
//                t.data?.categories?.let {
//                    activity.setUpCategory(t.data!!.categories!!)
//                }
//            }
//
//            override fun onApiLoadError(msg: String) {
//                activity.dialogDismiss()
//                activity.dialogErrorShow(msg, null)
//            }
//        })
    }
}