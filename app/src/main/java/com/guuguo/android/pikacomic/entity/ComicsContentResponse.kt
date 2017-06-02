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
    var pages: PagesEntity? = null
    var ep: EpEntity? = null
}

@Table("ep_page")
class PagesEntity {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    var _id = 0
    var total: Int = 0
    var limit: Int = 0
    var page: Int = 0
    var pages: Int = 0
    @Mapping(Relation.OneToMany)
    var docs: List<ImageEntity> = arrayListOf()
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