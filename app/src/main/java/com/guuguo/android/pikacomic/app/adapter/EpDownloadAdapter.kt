package com.guuguo.android.pikacomic.app.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.entity.EpEntity
import com.guuguo.android.pikacomic.entity.EpPagesEntity

class EpDownloadAdapter : BaseQuickAdapter<EpEntity, EpDownloadAdapter.ViewHolder> {


    constructor(data: List<EpEntity>) : super(R.layout.item_ep_download, data)

    constructor() : super(R.layout.item_ep_download, null)

//    val pageMap = HashMap<Int, ArrayList<EpPagesEntity>>()

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        val tv_title = getView<TextView>(R.id.tv_title)
        val tv_hint = getView<TextView>(R.id.tv_hint)
        val rtv_read = getView<TextView>(R.id.rtv_read)
    }

    override fun createBaseViewHolder(view: View): ViewHolder {
        val holder = ViewHolder(view)
        return holder
    }

    override fun convert(helper: ViewHolder, item: EpEntity) {
        val position = helper.layoutPosition
        val pages: ArrayList<EpPagesEntity>
//        if (!pageMap.containsKey(position)) {
            pages = EpPagesEntity.queryByEpOrder(item.order, item.comicId)
//            pageMap.put(position, pages)
//        } else {
//            pages = pageMap[position]!!
//        }

        helper.tv_title.text = item.title
        helper.tv_hint.text =
                if (pages.isEmpty()) {
                    "未开始下载"
                } else {
                    "${item.downloadCount}/${pages[0].total} ${if (item.downloadCount >= pages[0].total) "已完成" else "下载中"}"
                }

    }
}
