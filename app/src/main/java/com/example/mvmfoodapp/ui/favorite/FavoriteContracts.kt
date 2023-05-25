package com.example.mvmfoodapp.ui.favorite

import com.example.mvmfoodapp.data.db.FoodEntity

interface FavoriteContracts {

    interface View {
        fun loadFoodList(foodList: MutableList<FoodEntity>)
        fun emptyList()
        fun errorMessage(str: String)
    }

    interface Presenter {
        fun callFoodOperations()
    }
}