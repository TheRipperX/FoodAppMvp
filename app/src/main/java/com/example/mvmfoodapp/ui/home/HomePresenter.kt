package com.example.mvmfoodapp.ui.home

import com.example.mvmfoodapp.base.BasePresenterImpl
import com.example.mvmfoodapp.data.repository.home.HomeRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor(private val homeRepository: HomeRepository, private val view: HomeContracts.View):
    BasePresenterImpl(), HomeContracts.Presenter{


    override fun callFoodRandom() {

        if (view.checkInternet()) {

            disposable = homeRepository.loadFoodRandom()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({res->

                    res.let {response->
                        if (response.isSuccessful){
                            response.body().let {
                                view.loadFoodRandom(it!!)
                            }
                        }else {
                            view.serverError(response.errorBody().toString())
                        }
                    }

                },{err ->
                    view.serverError(err.message.toString())
                })

        }else {
            view.errorInternet(false)
        }
    }

    override fun callFoodCategory() {

        if (view.checkInternet()) {
            view.showLoading()
            disposable = homeRepository.loadCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({res->

                    res.let {response->
                        if (response.isSuccessful){
                            view.hideLoading()
                            response.body().let {
                                view.loadFoodCategory(it!!)
                            }
                        }else {
                            view.hideLoading()
                            view.serverError(response.errorBody().toString())
                        }
                    }

                },{err ->
                    view.hideLoading()
                    view.serverError(err.message.toString())
                })

        }else {
            view.hideLoading()
            view.errorInternet(false)
        }
    }

    override fun callFoodList(str: String) {

        if (view.checkInternet()) {
            view.showLFood()
            disposable = homeRepository.loadFoodList(str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({res->

                    res.let {response->
                        if (response.isSuccessful){
                            view.hideLFood()
                            response.body().let {
                                if (it!!.meals.isNullOrEmpty()){
                                    view.emptyList()
                                }else{
                                    view.loadFoodList(it)
                                }
                                //view.loadFoodList(it!!)
                            }
                        }else {
                            view.hideLFood()
                            view.serverError(response.errorBody().toString())
                        }
                    }

                },{err ->
                    view.hideLFood()
                    view.serverError(err.message.toString())
                })

        }else {
            view.hideLFood()
            view.errorInternet(false)
        }
    }

    override fun callSearchFoodList(str: String) {
        if (view.checkInternet()) {
            view.showLFood()
            disposable = homeRepository.loadSearchFood(str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({res->

                    res.let {response->
                        if (response.isSuccessful){
                            view.hideLFood()

                            response.body().let {
                                if (it!!.meals.isNullOrEmpty()){
                                    view.emptyList()
                                }else{
                                    view.loadFoodList(it)
                                }

                            }
                        }else {
                            view.hideLFood()
                            view.serverError(response.errorBody().toString())
                        }
                    }

                },{err ->
                    view.hideLFood()
                    view.serverError(err.message.toString())
                })

        }else {
            view.hideLFood()
            view.errorInternet(false)
        }
    }

    override fun callCategoryFoodList(str: String) {
        if (view.checkInternet()) {
            view.showLFood()
            disposable = homeRepository.loadCategoryFood(str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({res->

                    res.let {response->
                        if (response.isSuccessful){
                            view.hideLFood()
                            response.body().let {
                                view.loadFoodList(it!!)
                            }
                        }else {
                            view.hideLFood()
                            view.serverError(response.errorBody().toString())
                        }
                    }

                },{err ->
                    view.hideLFood()
                    view.serverError(err.message.toString())
                })

        }else {
            view.hideLFood()
            view.errorInternet(false)
        }
    }

    }


