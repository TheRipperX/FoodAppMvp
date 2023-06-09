package com.example.mvmfoodapp.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.mvmfoodapp.R
import com.example.mvmfoodapp.data.db.FoodEntity
import com.example.mvmfoodapp.data.model.detail.ResponseDetail
import com.example.mvmfoodapp.databinding.FragmentDetailBinding
import com.example.mvmfoodapp.utils.isCHeckInternet
import com.example.mvmfoodapp.utils.showSnackBar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(), DetailContracts.View {

    private lateinit var binding: FragmentDetailBinding

    private val args: DetailFragmentArgs by navArgs()
    private var idFood = 0

    @Inject
    lateinit var foodEntity: FoodEntity

    @Inject
    lateinit var presenter: DetailPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onStopApp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idFood = args.foodIds

        if (idFood > 0)
            presenter.callDetailFoodItems(idFood)

        binding.apply {
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun loadDetailFoodItems(data: ResponseDetail) {
        binding.apply {

            try {
                data.meals[0].let { listsFood->

                    //database
                    foodEntity.id = listsFood.idMeal.toInt()
                    foodEntity.foodName = listsFood.strMeal
                    foodEntity.foodImage = listsFood.strMealThumb

                    presenter.callIsFoods(listsFood.idMeal.toInt())

                    //server
                    imgFood.load(listsFood.strMealThumb)
                    imgModeFood.text = listsFood.strCategory
                    imgAreaFood.text = listsFood.strArea

                    //action view page
                    if (listsFood.strSource != null) {

                        if (listsFood.strSource.toString().isNotEmpty()){
                            imgPageFood.setOnClickListener {
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(listsFood.strSource.toString())))
                            }
                        }

                    }else{
                        imgPageFood.visibility = View.GONE
                    }


                    txtTitle.text = listsFood.strMeal
                    txtDesc.text = listsFood.strInstructions

                    val jsonAll = JSONObject(Gson().toJson(data))
                    val meals = jsonAll.getJSONArray("meals")
                    val meal = meals.getJSONObject(0)
                    for (i in 1..12){
                        try {
                            val ingredient = meal.getString("strIngredient$i")
                            if (ingredient.isNullOrEmpty().not()) {
                                txtInDesc.append("$ingredient\n")
                            }
                            val measure = meal.getString("strMeasure$i")
                            if (measure.isNullOrEmpty().not()) {
                                txtMeasDesc.append("$measure\n")
                            }
                        }catch (_: Exception) {

                        }
                    }
                }
            }catch (e: java.lang.Exception) {
                Toast.makeText(requireContext(), "Error Server Internal...", Toast.LENGTH_SHORT)
                    .show()
            }


        }
    }

    @SuppressLint("ResourceType")
    override fun loadFoodOperations(isAdded: Boolean) {
        binding.apply {
            imgFavorite.setOnClickListener {
                if (isAdded) {
                    presenter.callDeleteFood(foodEntity)
                }else{
                    presenter.callInsertFood(foodEntity)
                }
            }
            //check add or no
            if (isAdded){
                imgFavorite.setColorFilter(ContextCompat.getColor(requireContext(), R.color.purple_500))
            }else {
                imgFavorite.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    override fun showLoading() {
        binding.apply {
            progressDetail.visibility = View.VISIBLE
            layDetails.visibility = View.GONE
        }
    }

    override fun hideLoading() {
       binding.apply {
           progressDetail.visibility = View.GONE
           layDetails.visibility = View.VISIBLE
       }
    }

    override fun checkInternet(): Boolean {
        return requireContext().isCHeckInternet()
    }

    override fun errorInternet(hasInternet: Boolean) {

        binding.apply {

            if (hasInternet) {
                layDetails.visibility = View.VISIBLE
                conLayoutNoInternet.visibility = View.GONE

                if (idFood > 0)
                    presenter.callDetailFoodItems(idFood)

            }else {
                layDetails.visibility = View.GONE
                conLayoutNoInternet.visibility = View.VISIBLE
            }

        }

    }

    override fun serverError(message: String) {
        binding.root.showSnackBar(message)
    }
}