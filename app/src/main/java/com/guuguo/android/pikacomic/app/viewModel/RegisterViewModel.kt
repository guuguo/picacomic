package com.guuguo.android.pikacomic.app.viewModel

import android.app.Activity
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.guuguo.android.pikacomic.app.activity.RegisterFragment
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*


/**
 * guode 创造于 2017-05-21.
 * 项目 pika
 */
class RegisterViewModel(val fragment: RegisterFragment) : BaseObservable() {
    val activity = fragment.activity
    val dateInfo = ObservableField(DateInfo())

    data class DateInfo(var date: String = "", var dateStr: String = "出生日期 (未满18岁不能进入哦!)", var old: Int = 0) {
        fun generateDateStr(old: Int) {
            this.old = old
            dateStr = "出生日期: $date (${old}岁)"
        }
    }

    val dataFormat = SimpleDateFormat("yyyy-MM-dd")
    fun onDateCheck(v: View) {
        val now = Calendar.getInstance()
        if (dateInfo.get().date.isNullOrEmpty()) {
            now.add(Calendar.YEAR, -18)
        } else {
            now.time = dataFormat.parse(dateInfo.get().date)
        }
        val year = now.get(Calendar.YEAR)
        val month = now.get(Calendar.MONTH)
        val day = now.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener
        { _, year, monthOfYear, dayOfMonth ->
            val dateStr = "${year}-${monthOfYear + 1}-${dayOfMonth}"
            System.currentTimeMillis() - dataFormat.parse(dateStr).time
            dateInfo.get().date = dateStr
            dateInfo.get().generateDateStr(getAge(year, monthOfYear, dayOfMonth))
            dateInfo.notifyChange()
        }, year, month, day)
        datePickerDialog.setMessage("请选择日期")
        datePickerDialog.show()
    }

    fun getAge(yearBirth: Int, monthBirth: Int, dayOfMonthBirth: Int): Int {
        val cal = Calendar.getInstance()
        val yearNow = cal.get(Calendar.YEAR)
        val monthNow = cal.get(Calendar.MONTH)
        val dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH)
        var age = yearNow - yearBirth
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--
            } else {
                age--
            }
        }
        return age
    }

    fun register(email: String, password: String, gender: String, name: String) {
        activity.dialogLoadingShow("正在注册中")
        MyApiServer.register(email, password, dateInfo.get().date, gender, name).subscribe(object : BaseCallback<ResponseModel<String>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<String>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                LocalData.email = email
                LocalData.password = password
                activity.setResult(Activity.RESULT_OK)
                activity.dialogCompleteShow("注册成功，可以登陆了", DialogInterface.OnDismissListener {
                    activity.finish()
                })
            }

            override fun onApiLoadError(msg: String) {
                activity.dialogDismiss()
                activity.dialogErrorShow(msg, null)
            }
        })
    }
}