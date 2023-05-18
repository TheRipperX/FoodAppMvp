package com.example.mvmfoodapp.ui.detail

import com.example.mvmfoodapp.base.BasePresenter
import com.example.mvmfoodapp.base.BaseView
import com.example.mvmfoodapp.data.model.detail.ResponseDetail

interface DetailContracts {

    interface View: BaseView {
        fun loadDetailFoodItems(data: ResponseDetail)
    }

    interface Presenter: BasePresenter {
        fun callDetailFoodItems(id: Int)
    }
}