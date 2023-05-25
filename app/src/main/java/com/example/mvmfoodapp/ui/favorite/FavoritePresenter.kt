package com.example.mvmfoodapp.ui.favorite

import com.example.mvmfoodapp.base.BasePresenterImpl
import com.example.mvmfoodapp.data.repository.favorite.FavoriteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class FavoritePresenter @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val view: FavoriteContracts.View)
    : BasePresenterImpl(), FavoriteContracts.Presenter {


    override fun callFoodOperations() {
        disposable = favoriteRepository.showAllFood()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.isNotEmpty()){
                        view.loadFoodList(it)
                    }else {
                        view.emptyList()
                    }
                },{
                  view.errorMessage(it.message.toString())
                },{

                }
            )
    }

}