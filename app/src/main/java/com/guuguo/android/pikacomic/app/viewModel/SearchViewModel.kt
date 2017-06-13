package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import android.graphics.Color
import android.os.Build
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.FrameLayout
import com.flyco.systembar.SystemBarHelper
import com.guuguo.android.pikacomic.app.activity.ComicContentActivity
import com.guuguo.android.pikacomic.app.activity.SearchActivity
import com.guuguo.android.pikacomic.db.UOrm
import com.guuguo.android.pikacomic.entity.ComicsContentResponse
import com.guuguo.android.pikacomic.entity.EpPagesEntity
import com.guuguo.android.pikacomic.entity.ImageEntity
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class SearchViewModel(val activity: SearchActivity) : BaseObservable() {

}

