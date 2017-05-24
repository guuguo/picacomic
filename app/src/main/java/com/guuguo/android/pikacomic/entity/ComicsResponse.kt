package com.guuguo.android.pikacomic.entity

/**
 * guode 创造于 2017-05-24.
 * 项目 pika
 */

class ComicsResponse {

    var comics: ComicsBean? = null
    class ComicsBean {

        var total: Int = 0
        var limit: Int = 0
        var page: Int = 0
        var pages: Int = 0
        var docs: List<ComicsEntity> = arrayListOf()
    }
}
