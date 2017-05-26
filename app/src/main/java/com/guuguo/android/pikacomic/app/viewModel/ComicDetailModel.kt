package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.guuguo.android.lib.extension.date
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.toast
import com.guuguo.android.pikacomic.app.fragment.ComicDetailFragment
import com.guuguo.android.pikacomic.entity.ComicDetailResponse
import com.guuguo.android.pikacomic.entity.ComicsEntity
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import com.hesheng.orderpad.db.UOrm
import io.reactivex.disposables.Disposable
import java.util.*


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicDetailModel(val fragment: ComicDetailFragment) : BaseObservable() {
    val activity = fragment.activity
    val comic: ObservableField<ComicsEntity> = ObservableField()

    fun bindResult(result: ComicsEntity) {
        this.comic.set(result)
        fragment.setUpComic(result)
    }

    fun getUpdateTime(date: Date?): String {
        val str = date?.date()
        if (str.isNullOrEmpty())
            return ""
        else
            return str.safe() + "更新"
    }

    fun onClickRead(view: View) {

    }

    fun onClickFavorite(view: View) {

    }

    fun onClickLike(view: View) {

    }

    fun getComic(id: String) {
        MyApiServer.getComicDetail(id).subscribe(object : BaseCallback<ResponseModel<ComicDetailResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<ComicDetailResponse>) {
                super.onSuccess(t)
                t.data?.comic?.let {
                    UOrm.db().save(t.data?.comic)
//                    bindResult(t.data?.comic!!)
//                    fragment.setUpComic(t.data!!.comic!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                msg.toast()
            }
        })
    }
}