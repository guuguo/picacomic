package com.guuguo.android.pikacomic.entity

/**
 * mimi 创造于 2017-05-23.
 * 项目 pika
 */

class AnnouncementsResponse{
    var announcements: Response? = null

    class Response {
        var total: Int = 0
        var limit: Int = 0
        var page: String? = null
        var pages: Int = 0
        var docs: List<AnnouncementsEntity>? = null
    }
}
