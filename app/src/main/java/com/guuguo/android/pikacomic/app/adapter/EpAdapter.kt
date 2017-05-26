package com.guuguo.android.pikacomic.app.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guuguo.android.pikacomic.R

class EpAdapter : BaseQuickAdapter<String, EpAdapter.ViewHolder> {


    constructor(data: List<String>) : super(R.layout.item_ep, data)

    constructor() : super(R.layout.item_ep, null)

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        val tvEp = getView<TextView>(R.id.tv_ep)
    }

    override fun createBaseViewHolder(view: View): ViewHolder {
        val holder = ViewHolder(view)
        return holder
    }

    override fun convert(helper: ViewHolder, item: String) {
        helper.tvEp.text = item
    }
}
