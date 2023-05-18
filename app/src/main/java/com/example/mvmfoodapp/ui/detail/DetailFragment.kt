package com.example.mvmfoodapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
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


        Log.d("TAG", "onViewCreated: $idFood")
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
                        val ingredient = meal.getString("strIngredient$i")
                        if (ingredient.isNullOrEmpty().not()) {
                            txtInDesc.append("$ingredient\n")
                        }
                        val measure = meal.getString("strMeasure$i")
                        if (measure.isNullOrEmpty().not()) {
                            txtMeasDesc.append("$measure\n")
                        }
                    }
                }
            }catch (e: java.lang.Exception) {
                Toast.makeText(requireContext(), "Error Server Internal...", Toast.LENGTH_SHORT)
                    .show()
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