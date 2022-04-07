package com.example.myandroidrepo2.di.module

import com.example.myandroidrepo2.data.api.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    private val PARSE_ROOT = "https://api.openweathermap.org/data/2.5/"
    private val API_KEY = "56fc6c6cb76c0864b4cd055080568268"

    @Provides
    fun provideKeyInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        original.url.newBuilder()
            .addQueryParameter("appid", API_KEY)
            .build()
            .let { chain.proceed(
                original.newBuilder().url(it).build()
            ) }
    }


    @Provides
    fun provideOkHttp(
        interceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .cache(null)
            .addInterceptor(interceptor)
            .build()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideRetrofit(
        okHttp: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttp)
            .baseUrl(PARSE_ROOT)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    fun provideApi(
        retrofit: Retrofit
    ): APIService =
        retrofit.create(APIService::class.java)

}
