package com.guuguo.android.pikacomic.app.base

import android.view.View
import com.guuguo.android.lib.app.LBaseFragment
import com.guuguo.android.pikacomic.app.MyApplication

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseFragment : LBaseFragment() {

    protected var myApplication = MyApplication.instance
    protected var contentView: View? = null

}
