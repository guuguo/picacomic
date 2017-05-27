/**
 * guode 创造于 2017-05-01.
 * 项目 pika
 */
package com.guuguo.android.pikacomic.constant

import com.guuguo.android.pikacomic.app.MyApplication
import com.guuguo.android.util.Preference

object LocalData {
    private val mContext by lazy { MyApplication.instance!! }

    var token by Preference<String>(mContext, "")
    var randomComics by Preference<String>(mContext, "")
    var categories by Preference<String>(mContext, "")
    var announcement by Preference<String>(mContext, "")
    var mine by Preference<String>(mContext, "")

    fun initSps() {
        token = ""
        randomComics=""
        categories=""
    }
}
