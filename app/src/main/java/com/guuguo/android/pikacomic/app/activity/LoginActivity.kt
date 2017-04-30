package com.guuguo.android.pikacomic.app.activity

import android.support.v4.content.ContextCompat
import android.view.MenuItem
import com.guuguo.android.pikacomic.R
import com.guuguo.android.pikacomic.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_login
    }

    override fun getHeaderTitle(): String {
        return "登录"
    }

    override fun isNavigationButtonVisible(): Boolean {
        return false
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return false
    }
    override fun initView() {
        super.initView()
//        user_name.text = "adassadasd"
        toolbar.navigationIcon = ContextCompat.getDrawable(activity, R.drawable.ic_icecream)
        rtv_login.setOnClickListener {

        }
    }



}
