package com.guuguo.android.pikacomic.app.service

import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.guuguo.android.lib.app.LNBaseService
import com.guuguo.android.lib.extension.toast
import com.guuguo.android.pikacomic.app.MyApplication
import com.guuguo.android.pikacomic.db.UOrm
import com.guuguo.android.pikacomic.entity.ComicsContentResponse
import com.guuguo.android.pikacomic.entity.EpEntity
import com.guuguo.android.pikacomic.entity.EpPagesEntity
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable
import zlc.season.rxdownload2.RxDownload
import zlc.season.rxdownload2.entity.DownloadFlag

class UpdateService : LNBaseService() {
    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

    companion object {
        val ARG_COMIC_ID = "ARG_COMIC_ID"
        val ARG_EP = "ARG_EP"
        fun intentStart(context: Context, arrayList: ArrayList<Int>, comicId: String) {
            val intent = Intent(context, UpdateService::class.java)
            intent.putExtra(ARG_COMIC_ID, comicId)
            intent.putExtra(ARG_EP, arrayList)
            context.startService(intent)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val eps = intent.getSerializableExtra(ARG_EP) as ArrayList<Int>
        val comicId = intent.getStringExtra(ARG_COMIC_ID)

        eps.forEach {
            getContent(comicId, it, 1)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun getContent(id: String, ep: Int, page: Int) {
        val pageEntity = EpPagesEntity.get(id, ep, page)
        var epEntity = EpEntity.get(id, ep)

        if (pageEntity != null) {
            if (epEntity == null)
                epEntity = EpEntity(id, ep)
            downloadImages(epEntity, pageEntity, id, ep, page)
        } else
            getContentFromNet(id, ep, page)
    }

    fun getContentFromNet(id: String, ep: Int, page: Int) {
        MyApiServer.getComicsContent(id, ep, page).subscribe(object : BaseCallback<ResponseModel<ComicsContentResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                addDispose(d)
            }

            override fun onSuccess(t: ResponseModel<ComicsContentResponse>) {
                super.onSuccess(t)
                t.data?.pages?.let {
                    //保存epPageEntity
                    t.data?.pages?.comicId = id
                    t.data?.pages?.ep = ep
                    UOrm.db().save(t.data?.pages)

                    //保存epEntity
                    t.data?.ep?.save(ep, id)

                    downloadImages(t.data?.ep!!, t.data!!.pages!!, id, ep, page)

                }
            }

            override fun onApiLoadError(msg: String) {
            }
        })
    }

    private fun downloadImages(epEntity: EpEntity, epPagesEntity: EpPagesEntity, id: String, ep: Int, page: Int) {
        if (epEntity.downloadCount == -1)
            epEntity.downloadCount = 0
        epPagesEntity.docs.map { it.media!! }.filter { !it.isDownload }.apply {
            val dispose = RxDownload.getInstance(MyApplication.instance).serviceMultiDownload(id + ep,
                    *this.map { it.getOriginUrl() }.toTypedArray())
                    .subscribe({ "开始下载".toast() })
            addDispose(dispose)
        }.forEach {
            RxDownload.getInstance(MyApplication.instance).receiveDownloadStatus(it.getOriginUrl()).subscribe({ downloadEvent ->
                val flag = downloadEvent.flag
                when (flag) {
                    DownloadFlag.COMPLETED -> {
                        it.isDownload = true
                        epEntity.downloadCount++
                        "${epEntity.downloadCount}/${epPagesEntity.total}".toast()
                        UOrm.db().update(it)
                        UOrm.db().update(epEntity)
                    }
                }
            })
        }
        if (epPagesEntity.page < epPagesEntity.pages)
            getContent(id, ep, page + 1)
        else {

        }
    }


}
