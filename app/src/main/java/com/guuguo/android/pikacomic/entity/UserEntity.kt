package com.guuguo.android.pikacomic.entity

import com.litesuits.orm.db.annotation.Ignore
import com.litesuits.orm.db.annotation.Mapping
import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.enums.AssignType
import com.litesuits.orm.db.enums.Relation
import java.io.Serializable

/**
 * guode 创造于 2017-05-23.
 * 项目 pika
 */
@Table("user")
class UserEntity : Serializable {
    @PrimaryKey(AssignType.BY_MYSELF)
    var _id = ""
    var gender = ""
    var name = ""
    var isVerified: Boolean = false
    var exp: Int = 0
    var level: Int = 0
    @Mapping(Relation.OneToOne)
    var avatar: ThumbEntity? = null
//    @Mapping(Relation.OneToMany)
    @Ignore
    var characters: ArrayList<String>? = ArrayList<String>()
}