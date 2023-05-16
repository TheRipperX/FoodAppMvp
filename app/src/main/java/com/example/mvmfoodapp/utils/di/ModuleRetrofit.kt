package com.example.mvmfoodapp.utils.di

import com.example.mvmfoodapp.data.server.ApiServices
import com.example.mvmfoodapp.utils.BASEURL
import com.example.mvmfoodapp.utils.BODYMODULE
import com.example.mvmfoodapp.utils.HEADERMODULE
import com.example.mvmfoodapp.utils.TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleRetrofit {

    @Provides
    @Singleton
    fun proBaseUrl() = BASEURL

    @Provides
    @Singleton
    fun proTimeOut() = TIMEOUT

    @Provides
    @Singleton
    fun proGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    @Named(BODYMODULE)
    fun proBody() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @Named(HEADERMODULE)
    fun proHeader() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    @Provides
    @Singleton
    fun proClient(time: Long, @Named(BODYMODULE) body: HttpLoggingInterceptor, @Named(HEADERMODULE) header: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(body)
        .addInterceptor(header)
        .readTimeout(time, TimeUnit.SECONDS)
        .writeTimeout(time, TimeUnit.SECONDS)
        .connectTimeout(time, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun proRetrofit(url: String, gson: Gson, client: OkHttpClient): ApiServices =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
            .create(ApiServices::class.java)
}