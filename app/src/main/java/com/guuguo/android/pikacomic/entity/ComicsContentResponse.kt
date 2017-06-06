package com.guuguo.android.pikacomic.entity

import com.litesuits.orm.db.annotation.Ignore
import com.litesuits.orm.db.annotation.Mapping
import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.enums.AssignType
import com.litesuits.orm.db.enums.Relation

/**
 * mimi 创造于 2017-05-26.
 * 项目 pika
 */

class ComicsContentResponse {
    var pages: EpPagesEntity? = null
    var ep: EpEntity? = null
}



@Table("content_image")
class ImageEntity {
    @PrimaryKey(AssignType.BY_MYSELF)
    var _id: String? = null
    @Mapping(Relation.OneToOne)
    var media: ThumbEntity? = null

    @Ignore
    var ep: Int = 1
    @Ignore
    var position: Int = 0
    @Ignore
    var total: Int = 0
}