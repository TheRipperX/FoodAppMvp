package com.example.mvmfoodapp.data.repository.detail

import com.example.mvmfoodapp.data.db.FoodDao
import com.example.mvmfoodapp.data.db.FoodEntity
import com.example.mvmfoodapp.data.server.ApiServices
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiServices: ApiServices, private val dao: FoodDao) {

    fun loadDetailFood(id: Int) = apiServices.detailInfoFood(id)

    fun insertFood(foodEntity: FoodEntity) = dao.foodInsert(foodEntity)

    fun deleteFood(foodEntity: FoodEntity) = dao.foodDelete(foodEntity)

    fun foodIdFind(id: Int) = dao.findIdFood(id)
}