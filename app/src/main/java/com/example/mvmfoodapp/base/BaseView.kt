package com.example.mvmfoodapp.base

interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun checkInternet(): Boolean
    fun errorInternet(hasInternet: Boolean)
    fun serverError(message: String)

}