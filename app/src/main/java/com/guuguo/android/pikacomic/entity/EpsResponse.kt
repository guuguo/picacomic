package com.guuguo.android.pikacomic.entity

/**
 * guode 创造于 2017-05-26.
 * 项目 pika
 */

class EpsResponse {

    /**
     * eps : {"docs":[{"_id":"58f408bb9036563f4e72f74f","title":"單行本","order":1,"updated_at":"2017-04-16T14:38:30.875Z"}],"total":1,"limit":40,"page":1,"pages":1}
     */

    var eps: EpsBean? = null
    class EpsBean {
        var total: Int = 0
        var limit: Int = 0
        var page: Int = 0
        var pages: Int = 0
        var docs: List<EpEntity> = arrayListOf()

    }
}
