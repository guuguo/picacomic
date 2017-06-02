package com.guuguo.android.pikacomic.app.viewModel

import android.app.Activity
import android.databinding.BaseObservable
import android.view.View
import com.flyco.dialog.listener.OnBtnClickL
import com.guuguo.android.pikacomic.app.fragment.DownloadManageFragment
import com.guuguo.android.pikacomic.constant.LocalData


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class SettingViewModel(val fragment: DownloadManageFragment) : BaseObservable() {
    val activity = fragment.activity

    fun onLogoutClick(v: View) {
        activity.dialogWarningShow("是否退出当前账号", "否", "是", OnBtnClickL {
            LocalData.isLogin = false
            LocalData.patternStr = ""
            activity.setResult(Activity.RESULT_OK)
            activity.finish()
        })
    }
}