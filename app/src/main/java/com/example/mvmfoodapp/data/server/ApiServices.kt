package com.example.mvmfoodapp.data.server

import com.example.mvmfoodapp.data.model.home.ResponseCategory
import com.example.mvmfoodapp.data.model.home.ResponseFoodList
import com.example.mvmfoodapp.data.model.home.ResponseRandom
import com.example.mvmfoodapp.data.model.home.ResponseSearchFood
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("random.php")
    fun randomResponse(): Single<Response<ResponseRandom>>

    @GET("categories.php")
    fun categoryResponse(): Single<Response<ResponseCategory>>

    @GET("search.php")
    fun foodListResponse(@Query("f") str: String): Single<Response<ResponseFoodList>>

    @GET("search.php")
    fun searchFoodList(@Query("s") str: String): Single<Response<ResponseFoodList>>

    @GET("filter.php")
    fun categoryFoodList(@Query("c") str: String): Single<Response<ResponseFoodList>>
}