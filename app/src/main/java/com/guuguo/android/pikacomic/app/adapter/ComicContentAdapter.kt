package com.guuguo.android.pikacomic.app.adapter

import am.drawable.TextDrawable
import android.graphics.Color
import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.android.lib.ui.imageview.RatioImageView
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.constant.getFileDir
import com.guuguo.android.pikacomic.entity.ImageEntity
import com.guuguo.android.pikacomic.utils.ScaleContentImageViewTarget
import java.io.File

class ComicContentAdapter : BaseQuickAdapter<ImageEntity, ComicContentAdapter.ViewHolder> {


    constructor(data: List<ImageEntity>) : super(R.layout.item_comic_content, data)

    constructor() : super(R.layout.item_comic_content, null)

    var firstEpList = arrayListOf<ImageEntity>()

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        val rivContent = getView<RatioImageView>(R.id.riv_content)
    }

    override fun createBaseViewHolder(view: View): ViewHolder {
        val holder = ViewHolder(view)
        return holder
    }

    override fun convert(helper: ViewHolder, item: ImageEntity) {
        val drawable = TextDrawable(mContext, 300f, Color.GRAY, item.position.toString())
        if (item.media!!.isDownload) {
            val file = File(getFileDir() + "/" + item.media!!.path)

            Glide.with(mContext).load(Uri.fromFile(file))
        } else {
            Glide.with(mContext).load(item.media?.getOriginUrl())
        }
                .centerCrop().placeholder(drawable).into(ScaleContentImageViewTarget(helper.rivContent, item, comicId))
    }

    var comicId = ""
}
