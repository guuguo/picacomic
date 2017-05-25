package com.guuguo.android.pikacomic.entity

import java.io.Serializable

/**
 * mimi 创造于 2017-05-24.
 * 项目 pika
 */
class ComicsEntity:Serializable {
    var _id = ""
    var _creator: CreatorEntity? = null
    var title = ""
    var description = ""
    var thumb: ThumbEntity? = null
    var author = ""
    var chineseTeam = ""
    var pagesCount: Int = 0
    var epsCount: Int = 0
    var isFinished: Boolean = false
    var updated_at = ""
    var created_at = ""
    var viewsCount: Int = 0
    var likesCount: Int = 0
    var isIsFavourite: Boolean = false
    var isIsLiked: Boolean = false
    var commentsCount: Int = 0
    var categories: List<String> = arrayListOf()
    var tags: List<String> = arrayListOf()
}