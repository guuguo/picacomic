package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import android.view.View
import com.google.gson.reflect.TypeToken
import com.guuguo.android.lib.extension.toast
import com.guuguo.android.pikacomic.app.activity.SearchActivity
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.constant.myGson
import com.guuguo.android.pikacomic.entity.ComicsResponse
import com.guuguo.android.pikacomic.entity.KeyWordResponse
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class SearchViewModel(val activity: SearchActivity) : BaseObservable() {
    

    fun getHotKeysFromNet() {
        activity.binding.spbSmooth.visibility = View.VISIBLE
        MyApiServer.keywords().subscribe(object : BaseCallback<ResponseModel<KeyWordResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<KeyWordResponse>) {
                super.onSuccess(t)
                activity.binding.spbSmooth.visibility = View.GONE
                t.data?.keywords?.let {
                    LocalData.keywords = myGson.toJson(t.data?.keywords!!)
                    activity.setUpKeywords(t.data!!.keywords!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                activity.binding.spbSmooth.visibility = View.GONE
                msg.toast()
            }
        })
    }

    fun getHotKeys() {
        val keywordsStr = LocalData.keywords
        try {
            val keywords: ArrayList<String> = myGson.fromJson(keywordsStr, object : TypeToken<ArrayList<String>>() {}.type)
            activity.setUpKeywords(keywords)
        } catch (e: Exception) {
        }
        getHotKeysFromNet()
    }

    fun getComics(page: Int, query: String) {
        if (page == 1) {
            activity.dialogLoadingShow("正在加载漫画列表")
        }
        val sub = MyApiServer.searchComics(page, query).subscribe(object : BaseCallback<ResponseModel<ComicsResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<ComicsResponse>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                t.data?.comics?.let {
                    activity.setUpComicsPage(t.data!!.comics!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                activity.dialogErrorShow(msg, null)
            }
        })
    }
}

