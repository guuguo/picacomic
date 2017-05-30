/**
 * guode 创造于 2017-05-01.
 * 项目 pika
 */
package com.guuguo.android.pikacomic.constant

import com.guuguo.android.pikacomic.app.MyApplication
import com.guuguo.android.util.Preference

object LocalData {
    private val mContext by lazy { MyApplication.instance!! }

    var token by Preference(mContext, "")
    var randomComics by Preference(mContext, "")
    var categories by Preference(mContext, "")
    var announcement by Preference(mContext, "")
    var mine by Preference(mContext, "")
    var isLogin by Preference(mContext, false)
    var email by Preference(mContext, "")
    var password by Preference(mContext, "")
    var patternStr by Preference(mContext, "")

    fun initSps() {
        token = ""
        randomComics=""
        categories=""
    }
}
