package com.guuguo.gank.net

import com.guuguo.android.pikacomic.entity.*
import com.guuguo.android.pikacomic.net.http.ResponseModel
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by gaohailong on 2016/5/17.
 */
interface Service {
    @POST("/auth/sign-in")
    fun signIn(@Body requestBody: RequestBody): Single<ResponseModel<TokenResponse>>

    @GET("/announcements")
    fun getAnnouncements(@Query("page") page: Int): Single<ResponseModel<AnnouncementsResponse>>

    @GET("/categories")
    fun getCategory(): Single<ResponseModel<CategoryResponse>>

    @GET("/comics")
    fun getComics(@Query("page") page: Int, @Query("c") category: String?, @Query("s") s: String): Single<ResponseModel<ComicsResponse>>

    @GET("/comics/{id}")
    fun getComicDetail(@Path("id") id: String): Single<ResponseModel<ComicDetailResponse>>

    @GET("/comics/{id}/favourite")
    fun favoriteComic(@Path("id") id: String): Single<ResponseModel<ActionResponse>>

    @GET("/comics/{id}/order/{ep}/pages")
    fun getComicContent(@Path("id") id: String, @Path("ep") ep: Int, @Query("page") page: Int): Single<ResponseModel<ComicsContentResponse>>

    @GET("/comics/random")
    fun getComicsRandom(@Query("page") page: Int): Single<ResponseModel<ComicsRandomResponse>>

    @GET("/comics/leaderboard")
    fun getComicsRank(@Query("tt") tt: String = "H24", @Query("ct") ct: String = "VC"): Single<ResponseModel<ComicsRandomResponse>>

    @GET("/comics/search")
    fun getComicsSearch(@Query("page") page: Int, @Query("q") query: String): Single<ResponseModel<ComicsResponse>>
}
