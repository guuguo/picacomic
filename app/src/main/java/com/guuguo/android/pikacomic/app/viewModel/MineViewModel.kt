package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.guuguo.android.lib.extension.toast
import com.guuguo.android.pikacomic.app.fragment.ComicDownloadManageFragment
import com.guuguo.android.pikacomic.app.fragment.ComicsFragment
import com.guuguo.android.pikacomic.app.fragment.MineFragment
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.constant.myGson
import com.guuguo.android.pikacomic.db.UOrm
import com.guuguo.android.pikacomic.entity.ComicsEntity
import com.guuguo.android.pikacomic.entity.PunchInResponse
import com.guuguo.android.pikacomic.entity.UserEntity
import com.guuguo.android.pikacomic.entity.UserResponse
import com.guuguo.android.pikacomic.net.http.BaseCallback
import com.guuguo.android.pikacomic.net.http.ResponseModel
import com.guuguo.gank.net.MyApiServer
import com.litesuits.orm.db.assit.QueryBuilder
import io.reactivex.disposables.Disposable


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

    fun onPunchInClick(v: View) {
        punchIn()
    }

    fun onFavoriteClick(v: View) {
        ComicsFragment.intentTo(activity, ComicsFragment.TYPE_COMICS_MY_FAVORITE)
    }
    fun  onDownloadClick(v: View) {
        ComicDownloadManageFragment.intentTo(activity)
    }
    fun getHistoryComics(page: Int, limit: Int) {
        val comics = UOrm.db().query(QueryBuilder(ComicsEntity::class.java).whereNoEquals("lastReadTime", 0).appendOrderDescBy("lastReadTime").limit(page * limit, limit))
        fragment.setUpHistory(comics)
    }

    fun punchIn() {
        activity.dialogLoadingShow("动感光波")
        MyApiServer.punchIn().subscribe(object : BaseCallback<ResponseModel<PunchInResponse>>() {
            override fun onSubscribe(d: Disposable?) {
                activity.addApiCall(d)
            }

            override fun onSuccess(t: ResponseModel<PunchInResponse>) {
                super.onSuccess(t)
                activity.dialogDismiss()
                if (t.data?.res?.status == "ok") {
                    activity.dialogCompleteShow("击打成功", null)
                    user.get().isPunched = true
                    fragment.setUpMine(user.get())
                    getUserProfileFromNet()

                } else
                    activity.dialogErrorShow("击打失败", null)
            }

            override fun onApiLoadError(msg: String) {
                fragment.binding.spbSmooth.visibility = View.GONE
                activity.dialogDismiss()
                msg.toast()
            }
        })
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
        try {
            bindResult(myGson.fromJson(tempMineStr, UserEntity::class.java))
        } catch (e: Exception) {

        }
        getUserProfileFromNet()
    }
}