package com.guuguo.android.pikacomic.app.adapter

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.flyco.roundview.RoundTextView
import com.guuguo.android.pikacomic.R

class EpAdapter(var canDownLoadSelect: Boolean = false) : BaseQuickAdapter<Int, EpAdapter.ViewHolder>(R.layout.item_ep, null) {

    var readEp = 0
    val selectedEp: ArrayList<Int> = arrayListOf()

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        val tvEp = getView<RoundTextView>(R.id.tv_ep)
    }

    override fun createBaseViewHolder(view: View): ViewHolder {
        val holder = ViewHolder(view)
        return holder
    }

    override fun convert(helper: ViewHolder, item: Int) {
        helper.addOnClickListener(R.id.tv_ep)
        if (!canDownLoadSelect)
            if (item == readEp) {
                helper.tvEp.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.colorPrimary)
                helper.tvEp.setTextColor(Color.WHITE)
            } else {
                helper.tvEp.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.migray)
                helper.tvEp.setTextColor(Color.BLACK)
            }
        else {
            if (selectedEp.contains(item)) {
                helper.tvEp.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.black50)
                helper.tvEp.setTextColor(Color.WHITE)
            } else {
                helper.tvEp.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.migray)
                helper.tvEp.setTextColor(Color.BLACK)
            }
        }

        helper.tvEp.text = item.toString()
    }
}
