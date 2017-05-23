package com.guuguo.gank.net

import com.guuguo.android.pikacomic.entity.AnnouncementsResponse
import com.guuguo.android.pikacomic.entity.TokenResponse
import com.guuguo.android.pikacomic.net.ApiConfig
import com.guuguo.android.pikacomic.net.http.ResponseModel
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by gaohailong on 2016/5/17.
 */
interface Service {
    @POST(ApiConfig.url_sign_in)
    fun signIn(@Body  requestBody: RequestBody): Single<ResponseModel<TokenResponse>>
    @GET(ApiConfig.url_announcements)
    fun getAnnouncements(@Query("page") page:Int): Single<ResponseModel<AnnouncementsResponse>>
}
