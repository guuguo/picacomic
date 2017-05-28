package com.guuguo.android.pikacomic.entity

/**
 * mimi 创造于 2017-05-26.
 * 项目 pika
 */

class ComicsContentResponse {
    var pages: PagesEntity? = null
    var ep: EpEntity? = null

    class PagesEntity {
        var total: Int = 0
        var limit: Int = 0
        var page: Int = 0
        var pages: Int = 0
        var docs: List<ImageEntity> = arrayListOf()
    }
}

class ImageEntity {
    var _id: String? = null
    var media: ThumbEntity? = null

    var ep: Int = 1
    var position: Int = 0
    var total: Int = 0
}