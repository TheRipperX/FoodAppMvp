package com.example.mvmfoodapp.ui.home

import com.example.mvmfoodapp.base.BasePresenter
import com.example.mvmfoodapp.base.BaseView
import com.example.mvmfoodapp.data.model.home.ResponseCategory
import com.example.mvmfoodapp.data.model.home.ResponseFoodList
import com.example.mvmfoodapp.data.model.home.ResponseRandom

interface HomeContracts {

    interface View: BaseView {
        fun loadFoodRandom(data: ResponseRandom)
        fun loadFoodCategory(data: ResponseCategory)
        fun loadFoodList(data: ResponseFoodList)
        fun hideLFood()
        fun showLFood()
        fun emptyList()
    }

    interface Presenter: BasePresenter {
        fun callFoodRandom()
        fun callFoodCategory()
        fun callFoodList(str: String)
        fun callSearchFoodList(str: String)
        fun callCategoryFoodList(str: String)
    }
}