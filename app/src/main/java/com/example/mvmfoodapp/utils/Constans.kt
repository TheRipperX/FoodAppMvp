package com.example.mvmfoodapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.google.android.material.snackbar.Snackbar

const val BASEURL = "https://www.themealdb.com/api/json/v1/1/"
const val TIMEOUT = 60L

const val BODYMODULE = "bodyModule"
const val HEADERMODULE = "headerModule"


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