package com.example.mvmfoodapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.mvmfoodapp.R
import com.example.mvmfoodapp.data.model.home.ResponseCategory
import com.example.mvmfoodapp.data.model.home.ResponseFoodList
import com.example.mvmfoodapp.data.model.home.ResponseRandom
import com.example.mvmfoodapp.databinding.FragmentHomeBinding
import com.example.mvmfoodapp.ui.home.adapter.AdapterCategory
import com.example.mvmfoodapp.ui.home.adapter.AdapterFoodList
import com.example.mvmfoodapp.utils.isCHeckInternet
import com.example.mvmfoodapp.utils.showSnackBar
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import greyfox.rxnetwork.RxNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(), HomeContracts.View {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    lateinit var adapterCategory: AdapterCategory

    @Inject
    lateinit var adapterFoodList: AdapterFoodList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            //call api random image
            presenter.callFoodRandom()

            //call food list category
            presenter.callFoodCategory()

            //call fool list
            presenter.callFoodList("b")

            edtSearch.textChanges()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    //call api
                    presenter.callSearchFoodList(it.toString())

                }
        }

        // slider handel
        filterFoods()

        //check internet
        RxNetwork.init(requireContext()).observe()
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe {
                errorInternet(it.isConnected)
            }
    }

    private fun filterFoods() {
        val foodList = listOf('A'..'Z').flatten()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, foodList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerHome.adapter = adapter

        binding.spinnerHome.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //call api
                presenter.callFoodList(foodList[p2].toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onStopApp()
    }

    override fun loadFoodRandom(data: ResponseRandom) {
        binding.imgRand.load(data.meals[0].strMealThumb) {
            crossfade(true)
            crossfade(500)
            error(R.drawable.notfound)
        }
    }

    override fun loadFoodCategory(data: ResponseCategory) {
        binding.recCategoryList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterCategory
        }
        adapterCategory.differ.submitList(data.categories)
        adapterCategory.onItemClick {
            presenter.callCategoryFoodList(it.strCategory)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun loadFoodList(data: ResponseFoodList) {
        binding.apply {
            layoutCategoryAndFoodList.visibility = View.VISIBLE
            layoutEmptyInternet.visibility = View.GONE
            recFoodList.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterFoodList
            }
        }
        adapterFoodList.notifyDataSetChanged()
        adapterFoodList.differ.submitList(data.meals)


        if (!data.meals.isNullOrEmpty())
            binding.edtSearch.text.clear()
    }

    override fun hideLFood() {
        binding.progressFoodList.visibility = View.GONE
    }

    override fun showLFood() {
        binding.progressFoodList.visibility = View.VISIBLE
    }

    override fun emptyList() {
        binding.apply {
            layoutCategoryAndFoodList.visibility = View.GONE
            layoutEmptyInternet.visibility = View.VISIBLE

            includedLayout.imgEmptyOrInternet.setImageResource(R.drawable.notfound)
        }
    }

    override fun showLoading() {
        binding.progressbarCategory.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressbarCategory.visibility = View.GONE
    }

    override fun checkInternet(): Boolean {
        return requireContext().isCHeckInternet()
    }

    override fun errorInternet(hasInternet: Boolean) {

        binding.apply {

            if (hasInternet) {
                layoutCategoryAndFoodList.visibility = View.VISIBLE
                layoutFoodList.visibility = View.VISIBLE
                layoutEmptyInternet.visibility = View.GONE
                //call api random image
                presenter.callFoodRandom()

                //call food list category
                presenter.callFoodCategory()

                //call fool list
                presenter.callFoodList("b")

            }else{
                imgRand.setImageResource(R.drawable.no_internet)
                layoutCategoryAndFoodList.visibility = View.GONE
                layoutFoodList.visibility = View.GONE
                layoutEmptyInternet.visibility = View.VISIBLE
            }
        }
    }

    override fun serverError(message: String) {
        binding.root.showSnackBar(message)
    }


}