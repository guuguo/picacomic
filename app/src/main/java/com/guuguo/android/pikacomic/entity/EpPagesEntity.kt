package com.guuguo.android.pikacomic.entity

import com.guuguo.android.pikacomic.db.UOrm
import com.litesuits.orm.db.annotation.*
import com.litesuits.orm.db.assit.QueryBuilder
import com.litesuits.orm.db.enums.AssignType
import com.litesuits.orm.db.enums.Relation

/**
 * guode 创造于 2017-06-02.
 * 项目 pika
 */
@Table("ep_page")
class EpPagesEntity {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    var _id = 0

    var total: Int = 0
    @Column("_limit")
    var limit: Int = 0
    @UniqueCombine(1)
    var page: Int = 0

    var pages: Int = 0
    @Mapping(Relation.OneToMany)
    var docs: ArrayList<ImageEntity> = arrayListOf()

    @UniqueCombine(1)
    var comicId = ""

    @UniqueCombine(1)
    var ep = 1

    companion object {
        fun query(id: String, ep: Int, page: Int): EpPagesEntity? {
            val entities = UOrm.db()
                    .query(QueryBuilder(EpPagesEntity::class.java)
                            .whereEquals("comicId", id).whereAppendAnd().whereEquals("ep", ep)
                            .whereAppendAnd().whereEquals("page", page))
            return if (entities.isNotEmpty())
                entities.first()
            else null
        }

        fun queryFirstByEpOrder(order: Int, comicId: String): EpPagesEntity? {
            val entities = UOrm.db().single()
                    .query(QueryBuilder(EpPagesEntity::class.java)
                            .whereEquals("comicId", comicId).whereAppendAnd().whereEquals("ep", order)
                            .appendOrderAscBy("page").limit(0, 1))
            if (entities.isNotEmpty())
                return entities[0]
            else return null

        }
    }
}