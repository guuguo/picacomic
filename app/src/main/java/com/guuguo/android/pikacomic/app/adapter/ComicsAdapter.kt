package com.guuguo.android.pikacomic.app.adapter

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.entity.ComicsEntity

class ComicsAdapter : BaseQuickAdapter<ComicsEntity, ComicsAdapter.ViewHolder> {


    constructor(data: List<ComicsEntity>) : super(R.layout.item_comics, data)

    constructor() : super(R.layout.item_comics, null)

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        val iv_banner = getView<ImageView>(R.id.iv_banner)
        val tv_title = getView<TextView>(R.id.tv_title)
    }

    override fun createBaseViewHolder(view: View): ViewHolder {
        val holder = ViewHolder(view)
        return holder
    }

    val loading by lazy { ContextCompat.getDrawable(mContext,R.drawable.placeholder_loading)}
    override fun convert(helper: ViewHolder, item: ComicsEntity) {
        Glide.with(mContext).load(item.thumb?.getOriginUrl().safe()).centerCrop().placeholder(loading).into(helper.iv_banner)
        helper.tv_title.text = item.title
    }
}
