package com.guuguo.android.pikacomic.app.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.WindowManager
import com.guuguo.android.lib.app.LNBaseActivity
import com.guuguo.android.lib.utils.ScreenManager
import com.guuguo.android.pikacomic.app.MyApplication

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseActivity : LNBaseActivity() {
    protected var myApplication = MyApplication.instance
}
