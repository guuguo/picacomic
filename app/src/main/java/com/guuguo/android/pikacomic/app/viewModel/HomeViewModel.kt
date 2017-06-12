package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import android.view.View
import com.google.gson.reflect.TypeToken
import com.guuguo.android.lib.extension.toast
import com.guuguo.android.pikacomic.app.activity.SearchActivity
import com.guuguo.android.pikacomic.app.fragment.HomeFragment
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.constant.myGson
import com.guuguo.android.pikacomic.entity.AnnouncementsResponse
import com.guuguo.android.pikacomic.entity.ComicsEntity
import com.guuguo.android.pikacomic.entity.ComicsRandomResponse
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
   fun onSearchClick(v:View){
       SearchActivity.intentTo(activity,v)
   }
    fun getAnnouncementsFromNet() {
        fragment.binding.spbSmooth.visibility = View.VISIBLE
        MyApiServer.getAnnouncements().subscribe(object : BaseCallback<ResponseModel<AnnouncementsResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<AnnouncementsResponse>) {
                super.onSuccess(t)
                fragment.binding.spbSmooth.visibility = View.GONE
                t.data?.announcements?.let {
                    LocalData.announcement = myGson.toJson(t.data?.announcements!!)
                    fragment.setUpAnnouncementsBanner(t.data!!.announcements!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                fragment.binding.spbSmooth.visibility = View.GONE
                msg.toast()
            }
        })
    }

    fun getAnnouncements() {
//        val announcementStr = LocalData.announcement
//        try {
//            val announcement: AnnouncementsResponse.Response = myGson.fromJson(announcementStr, AnnouncementsResponse.Response::class.java)
//            fragment.setUpAnnouncementsBanner(announcement)
//        } catch (e: Exception) {
//        }
        getAnnouncementsFromNet()
    }

    fun getComicsRandom() {
        val randomComicsStr = LocalData.randomComics
        try {
            val comics: List<ComicsEntity> = myGson.fromJson(randomComicsStr, object : TypeToken<ArrayList<ComicsEntity>>() {}.type)
            fragment.setUpComics(comics)
        } catch (e: Exception) {
            getComicRandomFromNet()
        }
    }

    fun getComicRandomFromNet() {
        activity.dialogLoadingShow("正在加载中")
        MyApiServer.getComicsRandom(1).subscribe(object : BaseCallback<ResponseModel<ComicsRandomResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<ComicsRandomResponse>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                t.data?.comics?.let {
                    LocalData.randomComics = myGson.toJson(t.data?.comics)
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