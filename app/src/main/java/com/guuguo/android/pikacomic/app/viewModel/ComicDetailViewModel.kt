package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.guuguo.android.lib.extension.date
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.toast
import com.guuguo.android.pikacomic.app.fragment.ComicDetailFragment
import com.guuguo.android.pikacomic.db.UOrm
import com.guuguo.android.pikacomic.entity.ActionResponse
import com.guuguo.android.pikacomic.entity.ComicDetailResponse
import com.guuguo.android.pikacomic.entity.ComicsEntity
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import com.litesuits.orm.db.model.ConflictAlgorithm
import io.reactivex.disposables.Disposable
import java.util.*


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicDetailViewModel(val fragment: ComicDetailFragment) : BaseObservable() {
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
            return "更新:" + str.safe()
    }

    fun onClickFavorite(view: View) {
        actionComic(comic.get()._id, ACTION_FAVORITE)
    }

    fun onClickLike(view: View) {
        actionComic(comic.get()._id, ACTION_LIKE)
    }

    val ACTION_LIKE = 0
    val ACTION_FAVORITE = 1

    fun actionComic(id: String, action: Int) {
        activity.dialogLoadingShow("正在操作中")
        val sb = when (action) {
            ACTION_LIKE -> MyApiServer.likeComic(id)
            ACTION_FAVORITE -> MyApiServer.favoriteComic(id)
            else -> MyApiServer.favoriteComic(id)
        }
        sb.subscribe(object : BaseCallback<ResponseModel<ActionResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<ActionResponse>) {
                super.onSuccess(t)
                activity.dialogDismiss()
//                "操作成功".toast()
                when (action) {
                    ACTION_LIKE -> {
                        comic.get().isLiked = !comic.get().isLiked
                        if (comic.get().isLiked)
                            comic.get().likesCount++
                        else
                            comic.get().likesCount--
                        comic.notifyChange()
                    }
                    ACTION_FAVORITE -> {
                        comic.get().isFavourite = !comic.get().isFavourite
                        comic.notifyChange()
                    }
                }

            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                msg.toast()
            }
        })
    }

    fun getComic(id: String) {
        fragment.binding.spbSmooth.visibility = View.VISIBLE
        MyApiServer.getComicDetail(id).subscribe(object : BaseCallback<ResponseModel<ComicDetailResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<ComicDetailResponse>) {
                super.onSuccess(t)
                fragment.binding.spbSmooth.visibility = View.GONE
                t.data?.comic?.let {
                    t.data?.comic!!.save()
                    bindResult(t.data?.comic!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                fragment.binding.spbSmooth.visibility = View.GONE
                activity.dialogDismiss()
                msg.toast()
            }
        })
    }
}