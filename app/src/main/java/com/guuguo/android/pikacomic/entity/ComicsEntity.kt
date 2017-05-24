package com.guuguo.android.pikacomic.entity

/**
 * mimi 创造于 2017-05-24.
 * 项目 pika
 */
class ComicsEntity {
    /**
     * _id : 582185da5f6b9a4f93dc19df
     * title : あねいろ乳果汁 好色姊的乳果汁
     * author : はんぺら
     * pagesCount : 186
     * epsCount : 1
     * finished : true
     * categories : ["人妻","長篇","後宮閃光","姐姐系"]
     * thumb : {"fileServer":"https://storage1.picacomic.com","path":"c7dff0c6-1cb0-4f5d-b550-df4ab1ed7b3e.jpg","originalName":"_001.jpg"}
     * likesCount : 1435
     */

    var _id=""
    var title=""
    var author=""
    var pagesCount: Int = 0
    var epsCount: Int = 0
    var isFinished: Boolean = false
    var thumb: ThumbEntity? = null
    var likesCount: Int = 0
    var categories: List<String>? = null


}