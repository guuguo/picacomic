package com.guuguo.android.pikacomic.entity

import java.io.Serializable

/**
 * guode 创造于 2017-05-23.
 * 项目 pika
 */
class CreatorEntity : Serializable {
    var _id=""
    var gender=""
    var name=""
    var isVerified: Boolean = false
    var exp: Int = 0
    var level: Int = 0
    var avatar: ThumbEntity? = null
    var characters: List<String>? = arrayListOf()
}