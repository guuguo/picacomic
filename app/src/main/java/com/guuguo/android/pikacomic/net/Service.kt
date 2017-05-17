package com.guuguo.gank.net

import com.guuguo.android.pikacomic.entity.TokenResponse
import com.guuguo.android.pikacomic.net.ApiConfig
import com.guuguo.android.pikacomic.net.ResponseModel
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by gaohailong on 2016/5/17.
 */
interface Service {
    @POST(ApiConfig.url_sign_in)
    fun signIn(@Body  requestBody: RequestBody): Observable<ResponseModel<TokenResponse>>

}
