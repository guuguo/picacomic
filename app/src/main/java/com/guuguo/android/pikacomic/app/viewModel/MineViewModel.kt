package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.google.gson.reflect.TypeToken
import com.guuguo.android.lib.extension.date
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.extension.toast
import com.guuguo.android.pikacomic.app.fragment.ComicDetailFragment
import com.guuguo.android.pikacomic.app.fragment.MineFragment
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.constant.myGson
import com.guuguo.android.pikacomic.entity.*
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import com.hesheng.orderpad.db.UOrm
import io.reactivex.disposables.Disposable
import java.util.*


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class MineViewModel(val fragment: MineFragment) : BaseObservable() {
    val activity = fragment.activity
    val user: ObservableField<UserEntity> = ObservableField()

    fun bindResult(result: UserEntity) {
        this.user.set(result)
        fragment.setUpMine(result)
    }

    fun getUserProfileFromNet() {
        fragment.binding.spbSmooth.visibility = View.VISIBLE
        MyApiServer.userProfile().subscribe(object : BaseCallback<ResponseModel<UserResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<UserResponse>) {
                super.onSuccess(t)
                fragment.binding.spbSmooth.visibility = View.GONE
                t.data?.user?.let {
                    UOrm.db().save(t.data?.user)
                    bindResult(t.data?.user!!)
                }
            }

            override fun onApiLoadError(msg: String) {
                fragment.binding.spbSmooth.visibility = View.GONE
                activity.dialogDismiss()
                msg.toast()
            }
        })
    }

    fun getUserProfile() {
        val tempMineStr = LocalData.mine
        try{
            bindResult(myGson.fromJson(tempMineStr,UserEntity::class.java))
        }catch (e:Exception){
            
        }
        getUserProfileFromNet()
    }
}