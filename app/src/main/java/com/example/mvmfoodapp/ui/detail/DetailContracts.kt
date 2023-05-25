package com.example.mvmfoodapp.ui.detail

import com.example.mvmfoodapp.base.BasePresenter
import com.example.mvmfoodapp.base.BaseView
import com.example.mvmfoodapp.data.db.FoodEntity
import com.example.mvmfoodapp.data.model.detail.ResponseDetail

interface DetailContracts {

    interface View: BaseView {
        fun loadDetailFoodItems(data: ResponseDetail)

        //database
        fun loadFoodOperations(isAdded: Boolean)
    }

    interface Presenter: BasePresenter {
        fun callDetailFoodItems(id: Int)

        //database
        fun callInsertFood(foodEntity: FoodEntity)
        fun callDeleteFood(foodEntity: FoodEntity)
        fun callIsFoods(id: Int)
    }
}