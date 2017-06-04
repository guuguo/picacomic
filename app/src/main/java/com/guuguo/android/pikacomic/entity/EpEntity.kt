package com.guuguo.android.pikacomic.entity

import com.guuguo.android.pikacomic.db.UOrm
import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.assit.QueryBuilder
import com.litesuits.orm.db.enums.AssignType
import java.io.Serializable

/**
 * guode 创造于 2017-05-23.
 * 项目 pika
 */
@Table("ep")
class EpEntity(var comicId: String = "", var order: Int = 0) : Serializable {
    @PrimaryKey(AssignType.BY_MYSELF)
    var _id = ""
    var title = ""
    var updated_at = ""
    var downloadCount = 0

    fun save() {
        val entity = UOrm.db().queryById(_id, EpEntity::class.java)
        if (entity == null) {
            UOrm.db().insert(this)
        }else{
            this.comicId = entity.comicId
            this.downloadCount = entity.downloadCount
            UOrm.db().insert(this)
        }
    }

    companion object {
        fun get(comicId: String, order: Int): EpEntity? {
            val entities = UOrm.db().query(QueryBuilder(EpEntity::class.java).whereEquals("comicId", comicId).whereAppendAnd().whereEquals("order", order))
            return if (entities.isNotEmpty())
                entities.first()
            else null
        }
    }
}