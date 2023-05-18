package com.example.mvmfoodapp.ui.detail

import com.example.mvmfoodapp.base.BasePresenterImpl
import com.example.mvmfoodapp.data.repository.detail.DetailRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DetailPresenter @Inject constructor(
    private val detailRepository: DetailRepository,
    private val view: DetailContracts.View
) :
    BasePresenterImpl(), DetailContracts.Presenter {

    override fun callDetailFoodItems(id: Int) {
        if (view.checkInternet()) {
            view.showLoading()
            disposable = detailRepository.loadDetailFood(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {items ->
                        items.let {data->
                            if (data.isSuccessful) {
                                view.hideLoading()
                                data.body().let {
                                    if (!it!!.meals.isNullOrEmpty()){
                                        view.loadDetailFoodItems(it)
                                    }
                                }
                            }else {
                                view.hideLoading()
                                view.serverError(data.errorBody().toString())
                            }
                        }


                    }, {
                        view.hideLoading()
                        view.serverError(it.message.toString())
                    }, {
                        view.hideLoading()
                    })

        } else {
            view.hideLoading()
            view.errorInternet(false)
        }
    }
}