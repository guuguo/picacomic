package com.guuguo.android.pikacomic.entity

import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.enums.AssignType
import java.io.Serializable

/**
 * guode 创造于 2017-05-23.
 * 项目 pika
 */
@Table("thumb")
class ThumbEntity : Serializable {
    /**
     * originalName : translate.png
     * path : f541d9aa-e4fd-411d-9e76-c912ffc514d1.png
     * fileServer : https://storage1.picacomic.com
     */
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    var id = 0
    var originalName = ""
    var path = ""
    var fileServer = ""
    fun getOriginUrl(): String = fileServer + "/static/" + path

    var isDownload = false
}