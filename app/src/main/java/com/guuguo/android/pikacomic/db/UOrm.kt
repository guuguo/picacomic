package com.hesheng.orderpad.db

import android.database.sqlite.SQLiteDatabase
import com.guuguo.android.pikacomic.app.MyApplication
import com.litesuits.orm.LiteOrm
import com.litesuits.orm.db.DataBaseConfig
import com.litesuits.orm.db.assit.SQLiteHelper

/**
 * author: baiiu
 * date: on 16/5/18 17:43
 * description:
 *
 *
 * 封装数据库,对外提供基础操作
 *
 *
 *
 *
 * LiteOrm支持级联查询,对象关系映射为数据库关系
 */
object UOrm : SQLiteHelper.OnUpdateListener {

    private val mLiteOrm: LiteOrm
    internal var config = DataBaseConfig(MyApplication.instance)
    internal var isDbUpdate = false

    init {
        config.dbVersion = 2//新增拼音字段,拼音首字母大写模式
        config.debugged = true
        config.onUpdateListener = this
        mLiteOrm = LiteOrm.newCascadeInstance(config)
        if (isDbUpdate)
            updateDb()
    }

    override fun onUpdate(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        if (i1 > i) {
            isDbUpdate = true
        }
    }

    private fun updateDb() {

    }

    fun db(): LiteOrm {
        return mLiteOrm
    }


   
}
