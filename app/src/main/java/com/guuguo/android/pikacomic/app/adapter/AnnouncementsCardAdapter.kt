package com.guuguo.android.pikacomic.app.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.entity.AnnouncementsEntity
import com.view.jameson.library.CardAdapterHelper

class AnnouncementsCardAdapter : BaseQuickAdapter<AnnouncementsEntity, AnnouncementsCardAdapter.ViewHolder> {

    private val mCardAdapterHelper = CardAdapterHelper()

    constructor(data: List<AnnouncementsEntity>) : super(R.layout.item_announcement, data)

    constructor() : super(R.layout.item_announcement, null)

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        val iv_banner = getView<ImageView>(R.id.iv_banner)
        val tv_title = getView<TextView>(R.id.tv_title)
        val tv_content = getView<TextView>(R.id.tv_content)
    }

    override fun createBaseViewHolder(view: View): ViewHolder {
        val holder = ViewHolder(view)
        mCardAdapterHelper.onCreateViewHolder(recyclerView, holder.itemView)
        return holder
    }

    override fun convert(helper: ViewHolder, item: AnnouncementsEntity) {

        mCardAdapterHelper.onBindViewHolder(helper.itemView, helper.layoutPosition, itemCount)
        Glide.with(mContext).load(item.thumb?.getOriginUrl().safe()).asBitmap().placeholder(R.drawable.placeholder_loading).centerCrop().into(helper.iv_banner)
        helper.tv_title.text = item.title
        helper.tv_content.text = item.content
    }
}
