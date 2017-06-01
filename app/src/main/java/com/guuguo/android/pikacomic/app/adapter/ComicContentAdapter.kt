package com.guuguo.android.pikacomic.app.adapter

import am.drawable.TextDrawable
import android.graphics.Color
import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.android.lib.view.RatioPhotoView
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.entity.ImageEntity
import com.guuguo.android.pikacomic.utils.MyScaleImageViewTarget

class ComicContentAdapter : BaseQuickAdapter<ImageEntity, ComicContentAdapter.ViewHolder> {


    constructor(data: List<ImageEntity>) : super(R.layout.item_comic_content, data)

    constructor() : super(R.layout.item_comic_content, null)

    var firstEpList = arrayListOf<ImageEntity>()
    var secondEpList = arrayListOf<ImageEntity>()
//    var epInvisibleSize = 0

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        val rivContent = getView<RatioPhotoView>(R.id.riv_content)
    }

    override fun createBaseViewHolder(view: View): ViewHolder {
        val holder = ViewHolder(view)
        return holder
    }

    override fun convert(helper: ViewHolder, item: ImageEntity) {
        val drawable = TextDrawable(mContext, 300f, Color.GRAY, item.position.toString())
        Glide.with(mContext).load(item.media?.getOriginUrl()).centerCrop().placeholder(drawable).into(MyScaleImageViewTarget(helper.rivContent))
    }
}
