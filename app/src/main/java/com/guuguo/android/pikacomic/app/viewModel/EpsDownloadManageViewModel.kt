package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import com.guuguo.android.pikacomic.app.fragment.ComicsDownloadManageFragment
import com.guuguo.android.pikacomic.app.fragment.EpsDownloadManageFragment
import com.guuguo.android.pikacomic.db.UOrm
import com.guuguo.android.pikacomic.entity.ComicsEntity
import com.guuguo.android.pikacomic.entity.EpEntity
import com.litesuits.orm.db.assit.QueryBuilder


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class EpsDownloadManageViewModel(val fragment: EpsDownloadManageFragment) : BaseObservable() {
    val activity = fragment.activity
    fun getDownloadComics() {
        val eps = UOrm.db().query(QueryBuilder(EpEntity::class.java).whereEquals("comicId", 0).appendOrderAscBy("epOrder"))
        fragment.setUpDownload(eps)
    }


}

