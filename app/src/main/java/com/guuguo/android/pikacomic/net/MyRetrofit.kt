package com.guuguo.gank.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.guuguo.android.pikacomic.constant.LocalData
import com.guuguo.android.pikacomic.net.ApiConfig
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Created by gaohailong on 2016/5/17.
 */
object MyRetrofit {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val httpClient = OkHttpClient.Builder().addInterceptor({
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

    fun getGsonConverter(gsonBuilder: GsonBuilder?): GsonConverterFactory {
        val gson: Gson
        if (gsonBuilder != null)
            gson = gsonBuilder.setDateFormat(pattern).create()
        else {
            gson = GsonBuilder().setDateFormat(pattern).create()
        }
        return retrofit2.converter.gson.GsonConverterFactory.create(gson)
    }

    fun getRetrofit(gsonBuilder: com.google.gson.GsonBuilder? = null): retrofit2.Retrofit {
        return retrofit2.Retrofit.Builder()
                .baseUrl(ApiConfig.baseUrl)
                .addConverterFactory(getGsonConverter(gsonBuilder))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(MyRetrofit.httpClient)
                .build()
    }
}
