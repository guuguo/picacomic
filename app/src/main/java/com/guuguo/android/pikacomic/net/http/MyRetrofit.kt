package com.guuguo.gank.net

import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.constant.myGson
import com.guuguo.android.pikacomic.net.ApiConfig
import com.guuguo.android.pikacomic.net.https.TrustAllCerts
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by gaohailong on 2016/5/17.
 */
object MyRetrofit {
    val httpClient = OkHttpClient.Builder()
            .sslSocketFactory(TrustAllCerts.createSSLSocketFactory())
            .hostnameVerifier(TrustAllCerts.TrustAllHostnameVerifier())
            .connectTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor({
                chain ->
                val original = chain.request();
                val request = original.newBuilder()
                        .header("api-key", "C69BAF41DA5ABD1FFEDC6D2FEA56B")
                        .header("accept", "application/vnd.picacomic.com.v1+json")
                        .header("app-version", "2.0.3.13")
                        .header("app-uuid", UUID.randomUUID().toString())
                        .header("app-platform", "android")
                        .header("app-build-version", "29")
                        .header("User-Agent", "okhttp/3.2.0")
                        .header("authorization", LocalData.token)
                        .method(original.method(), original.body())
                        .build();
                chain.proceed(request);
            }).build()

    fun getGsonConverter(): GsonConverterFactory {
        return retrofit2.converter.gson.GsonConverterFactory.create(myGson)
    }


    fun getRetrofit(): retrofit2.Retrofit {
        return retrofit2.Retrofit.Builder()
                .baseUrl(ApiConfig.baseUrl)
                .addConverterFactory(getGsonConverter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(MyRetrofit.httpClient)
                .build()
    }

}