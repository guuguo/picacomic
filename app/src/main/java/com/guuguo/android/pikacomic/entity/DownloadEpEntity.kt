package com.guuguo.android.pikacomic.entity

import com.litesuits.orm.db.annotation.Mapping
import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.annotation.UniqueCombine
import com.litesuits.orm.db.enums.AssignType
import com.litesuits.orm.db.enums.Relation
import java.io.Serializable

/**
 * mimi 创造于 2017-05-24.
 * 项目 pika
 */
@Table("downloadEp")
class DownloadEpEntity : Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    var _id = 0

    @UniqueCombine(1)
    var comic_id = ""
    @UniqueCombine(1)
    var order = 1
    
    @Mapping(Relation.OneToMany)
    var comicContents: ArrayList<PagesEntity> = arrayListOf()
}