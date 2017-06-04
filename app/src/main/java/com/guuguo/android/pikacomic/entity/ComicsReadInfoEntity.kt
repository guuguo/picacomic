package com.guuguo.android.pikacomic.entity

import com.litesuits.orm.db.annotation.Default
import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.enums.AssignType
import java.io.Serializable

/**
 * mimi 创造于 2017-05-24.
 * 项目 pika
 */
@Table("comicsreadinfo")
class ComicsReadInfoEntity : Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    var _id = 0

    var readEp: Int? = null
    @Default("0")
    var lastReadTime: Long = 0


}