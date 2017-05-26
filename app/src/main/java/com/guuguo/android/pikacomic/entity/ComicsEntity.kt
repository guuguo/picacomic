package com.guuguo.android.pikacomic.entity

import com.litesuits.orm.db.annotation.Ignore
import com.litesuits.orm.db.annotation.Mapping
import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.enums.AssignType
import com.litesuits.orm.db.enums.Relation
import java.io.Serializable
import java.util.*

/**
 * mimi 创造于 2017-05-24.
 * 项目 pika
 */
@Table("comics")
class ComicsEntity : Serializable {
    @PrimaryKey(AssignType.BY_MYSELF)
    var _id = ""

    @Mapping(Relation.OneToOne)
    var _creator: UserEntity? = null
    var title = ""
    var description = ""

    @Mapping(Relation.OneToOne)
    var thumb: ThumbEntity? = null
    var author = ""
    var chineseTeam = ""
    var pagesCount: Int = 0
    var epsCount: Int = 0
    var isFinished: Boolean = false
    var updated_at: Date? = null
    var created_at: Date? = null
    var viewsCount: Int = 0
    var likesCount: Int = 0
    var isFavourite: Boolean = false
    var isLiked: Boolean = false
    var commentsCount: Int = 0
    //    @Mapping(Relation.OneToMany)
    @Ignore
    var categories: ArrayList<String> = ArrayList<String>()
    //    @Mapping(Relation.OneToMany)
    @Ignore
    var tags: ArrayList<String> = ArrayList<String>()
}