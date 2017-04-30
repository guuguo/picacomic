package com.guuguo.android.pikacomic.app.base

import com.guuguo.android.lib.app.LBaseActivity
import com.guuguo.android.pikacomic.app.MyApplication

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseActivity : LBaseActivity() {
    protected var myApplication = MyApplication.instance

}
