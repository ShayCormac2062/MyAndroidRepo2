package com.example.myandroidrepo2.di

import com.example.myandroidrepo2.data.api.APIService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DIContainer {

    private val PARCE_ROOT = "https://api.openweathermap.org/data/2.5/"
    private val API_KEY = "56fc6c6cb76c0864b4cd055080568268"

    private val keyInterceptor = Interceptor { chain ->
        val original = chain.request()
        original.url.newBuilder()
            .addQueryParameter("appid", API_KEY)
            .build()
            .let { chain.proceed(
                original.newBuilder().url(it).build()
            ) }
    }

    private val okhttp by lazy {
        OkHttpClient.Builder()
            .addInterceptor(keyInterceptor)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(okhttp)
            .baseUrl(PARCE_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: APIService by lazy {
        retrofit.create(APIService::class.java)
    }

}
