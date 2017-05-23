package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import com.guuguo.android.pikacomic.app.fragment.HomeFragment
import com.guuguo.android.pikacomic.entity.AnnouncementsResponse
import com.guuguo.android.pikacomic.entity.CategoryResponse
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class HomeViewModel(val fragment: HomeFragment) : BaseObservable() {
    val activity = fragment.activity
    fun getAnnouncements() {
        activity.dialogLoadingShow("正在加载中")
        MyApiServer.getAnnouncements().subscribe(object : BaseCallback<ResponseModel<AnnouncementsResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<AnnouncementsResponse>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                t.data?.announcements?.let {
                    fragment.setUpAnnouncementsBanner(t.data!!.announcements!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                activity.dialogErrorShow(msg, null)
            }
        })
    }
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