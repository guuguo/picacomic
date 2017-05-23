package com.guuguo.android.pikacomic.entity

/**
 * mimi 创造于 2017-05-23.
 * 项目 pika
 */

class AnnouncementsEntity {

    /**
     * _id : 5902f3c5685823089878b2e8
     * title : 我服務器君成長了
     * content : 我服務器君升級了，比以前快 0.01%
     * created_at : 2017-04-28T07:48:21.049Z
     * thumb : {"originalName":"serverkun.jpg","path":"2593f839-e231-43d5-9068-f5ef42997764.jpg","fileServer":"https://storage1.picacomic.com"}
     */

    var _id: String? = null
    var title: String? = null
    var content: String? = null
    var created_at: String? = null
    var thumb: ThumbEntity? = null

    class ThumbEntity {
        /**
         * originalName : serverkun.jpg
         * path : 2593f839-e231-43d5-9068-f5ef42997764.jpg
         * fileServer : https://storage1.picacomic.com
         */
        var originalName: String? = null
        var path: String? = null
        var fileServer: String? = null
        fun getOriginUrl():String= fileServer+"/"+path
    }
}
