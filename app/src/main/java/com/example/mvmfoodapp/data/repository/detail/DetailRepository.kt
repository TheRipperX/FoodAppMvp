package com.example.mvmfoodapp.data.repository.detail

import com.example.mvmfoodapp.data.server.ApiServices
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiServices: ApiServices) {

    fun loadDetailFood(id: Int) = apiServices.detailInfoFood(id)
}