package com.guuguo.android.pikacomic.app.adapter

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.flyco.roundview.RoundTextView
import com.guuguo.android.pikacomic.R

class EpAdapter : BaseQuickAdapter<String, EpAdapter.ViewHolder> {

    var readEp = 0

    constructor(data: List<String>) : super(R.layout.item_ep, data)

    constructor() : super(R.layout.item_ep, null)

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        val tvEp = getView<RoundTextView>(R.id.tv_ep)
    }

    override fun createBaseViewHolder(view: View): ViewHolder {
        val holder = ViewHolder(view)
        return holder
    }

    override fun convert(helper: ViewHolder, item: String) {
        if (item == readEp.toString()) {
            helper.tvEp.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.colorPrimary)
            helper.tvEp.setTextColor(Color.WHITE)
        } else {
            helper.tvEp.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.migray)
            helper.tvEp.setTextColor(Color.BLACK)
        }
        helper.tvEp.text = item
    }
}
