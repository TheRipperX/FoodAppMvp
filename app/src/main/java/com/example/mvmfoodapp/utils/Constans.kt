package com.example.mvmfoodapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.example.mvmfoodapp.data.model.home.ResponseFoodList
import com.google.android.material.snackbar.Snackbar

const val BASEURL = "https://www.themealdb.com/api/json/v1/1/"
const val TIMEOUT = 60L

const val BODYMODULE = "bodyModule"
const val HEADERMODULE = "headerModule"

const val BundleIdMeal = "bundle_id_meal"


fun Context.isCHeckInternet(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = cm.activeNetworkInfo
    return info != null && info.isConnected
}

/*fun showSnackBar(view: View, message: String){
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}*/

fun View.showSnackBar(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
}

class DifferAdapterApp(private val olds: List<ResponseFoodList.Meal>, private val news: List<ResponseFoodList.Meal>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return olds.size
    }

    override fun getNewListSize(): Int {
        return news.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return olds[oldItemPosition].idMeal == news[newItemPosition].idMeal
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return olds[oldItemPosition].idMeal == news[newItemPosition].idMeal
    }


}