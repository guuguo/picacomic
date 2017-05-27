package com.guuguo.android.pikacomic.entity

import java.util.*

/**
 * mimi 创造于 2017-05-27.
 * 项目 pika
 */

class PunchInResponse {

    var res: ResEntity? = null
    class ResEntity {
        var status=""
        var punchInLastDay: Date? = null
    }
}
