package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import android.view.View
import com.guuguo.android.pikacomic.app.fragment.ComicDetailFragment
import com.guuguo.android.pikacomic.app.fragment.EpsDownloadManageFragment
import com.guuguo.android.pikacomic.entity.EpEntity


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class EpsDownloadManageViewModel(val fragment: EpsDownloadManageFragment) : BaseObservable() {
    val activity = fragment.activity
    fun getDownloadEps(comicId:String) {
        val eps =EpEntity.queryByComicId(comicId) 
        fragment.setUpDownload(eps)
    }
  fun  onComicDetailClick(v: View){
      ComicDetailFragment.intentTo(activity,fragment.comicEntity)
  }
   
}

