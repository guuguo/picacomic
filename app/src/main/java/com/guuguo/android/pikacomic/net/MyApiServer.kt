package com.guuguo.gank.net

import com.google.gson.GsonBuilder
import com.guuguo.android.lib.net.LBaseCallback
import com.guuguo.android.pikacomic.entity.AnnouncementsResponse
import com.guuguo.android.pikacomic.entity.CategoryResponse
import com.guuguo.android.pikacomic.entity.TokenResponse
import com.guuguo.android.pikacomic.net.ApiConfig
import com.guuguo.android.pikacomic.net.http.ResponseModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*

/**
 * Created by guodeqing on 7/14/16.
 */
object MyApiServer {
    val gson = GsonBuilder().setDateFormat(ApiConfig.jsonDataFormatStr).create()
    val service by lazy { MyRetrofit.getRetrofit().create(Service::class.java) }
    fun signIn(email: String, password: String): Single<ResponseModel<TokenResponse>> {
        val map = HashMap<String, String>()
        map.put("email", email)
        map.put("password", password)

        return service.signIn(getRequestJsonBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAnnouncements(): Single<ResponseModel<AnnouncementsResponse>> {
        return service.getAnnouncements(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCategory(): Single<ResponseModel<CategoryResponse>> {
        return service.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
    
    private fun getRequestJsonBody(map: HashMap<String, String>): RequestBody = RequestBody.create(
            MediaType.parse("application/json; charset=UTF-8"),
            LBaseCallback.gson.toJson(map))
}
