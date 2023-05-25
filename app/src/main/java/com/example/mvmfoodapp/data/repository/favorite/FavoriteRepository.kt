package com.example.mvmfoodapp.data.repository.favorite

import com.example.mvmfoodapp.data.db.FoodDao
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao: FoodDao) {

    fun showAllFood() = dao.foodAllShow()
}