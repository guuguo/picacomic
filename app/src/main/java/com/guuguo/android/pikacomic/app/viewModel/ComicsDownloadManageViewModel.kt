package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import com.guuguo.android.pikacomic.app.fragment.ComicsDownloadManageFragment
import com.guuguo.android.pikacomic.db.UOrm
import com.guuguo.android.pikacomic.entity.ComicsEntity
import com.litesuits.orm.db.assit.QueryBuilder


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicsDownloadManageViewModel(val fragment: ComicsDownloadManageFragment) : BaseObservable() {
    val activity = fragment.activity
    fun getDownloadComics() {
        val comics = UOrm.db().query(QueryBuilder(ComicsEntity::class.java).whereNoEquals("addDownloadTime", 0).appendOrderDescBy("addDownloadTime"))
        fragment.setUpDownload(comics)
    }

   
}

