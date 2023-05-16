package com.example.mvmfoodapp.data.repository.home

import com.example.mvmfoodapp.data.server.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: ApiServices){

    fun loadFoodRandom() = api.randomResponse()

    fun loadCategory() = api.categoryResponse()

    fun loadFoodList(str: String) = api.foodListResponse(str)

    fun loadSearchFood(str: String) = api.searchFoodList(str)

    fun loadCategoryFood(str: String) = api.categoryFoodList(str)
}