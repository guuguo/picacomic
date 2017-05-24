package com.guuguo.gank.net

import com.guuguo.android.pikacomic.entity.AnnouncementsResponse
import com.guuguo.android.pikacomic.entity.CategoryResponse
import com.guuguo.android.pikacomic.entity.ComicsResponse
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
    fun signIn(@Body requestBody: RequestBody): Single<ResponseModel<TokenResponse>>

    @GET(ApiConfig.url_announcements)
    fun getAnnouncements(@Query("page") page: Int): Single<ResponseModel<AnnouncementsResponse>>

    @GET(ApiConfig.url_category)
    fun getCategory(): Single<ResponseModel<CategoryResponse>>

    @GET(ApiConfig.url_comics)
    fun getComics(@Query("page") page: Int, @Query("c") category: String?, @Query("s") s: String): Single<ResponseModel<ComicsResponse>>

    @GET(ApiConfig.url_comics_random)
    fun getComicsRandom(@Query("page") page: Int): Single<ResponseModel<ComicsResponse>>

    @GET(ApiConfig.url_comics_search)
    fun getComicsSearch(@Query("page") page: Int, @Query("q") query: String): Single<ResponseModel<ComicsResponse>>
}
