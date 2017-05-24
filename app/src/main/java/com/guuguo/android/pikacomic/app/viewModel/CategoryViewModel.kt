package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import com.guuguo.android.pikacomic.app.fragment.CategoryFragment
import com.guuguo.android.pikacomic.app.fragment.HomeFragment
import com.guuguo.android.pikacomic.entity.AnnouncementsResponse
import com.guuguo.android.pikacomic.entity.CategoryResponse
import com.guuguo.android.pikacomic.entity.ComicsResponse
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class CategoryViewModel(val fragment: CategoryFragment) : BaseObservable() {
    val activity = fragment.activity
   
    fun getCategory() {
        activity.dialogLoadingShow("正在加载中")
        MyApiServer.getCategory().subscribe(object : BaseCallback<ResponseModel<CategoryResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<CategoryResponse>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                t.data?.categories?.let {
                    fragment.setUpCategory(t.data!!.categories!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                activity.dialogErrorShow(msg, null)
            }
        })
    }
}