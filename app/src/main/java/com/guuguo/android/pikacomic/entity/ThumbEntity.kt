package com.guuguo.android.pikacomic.entity

/**
 * guode 创造于 2017-05-23.
 * 项目 pika
 */
class ThumbEntity {
    /**
     * originalName : translate.png
     * path : f541d9aa-e4fd-411d-9e76-c912ffc514d1.png
     * fileServer : https://storage1.picacomic.com
     */

    var originalName = ""
    var path = ""
    var fileServer = ""
    fun getOriginUrl():String= fileServer+"/static/"+path
}