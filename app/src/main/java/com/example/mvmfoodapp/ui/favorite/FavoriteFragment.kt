package com.example.mvmfoodapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvmfoodapp.R
import com.example.mvmfoodapp.data.db.FoodEntity
import com.example.mvmfoodapp.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment(), FavoriteContracts.View {

    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var presenter: FavoritePresenter

    @Inject
    lateinit var adapterFavorite: AdapterFavorite

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onStopApp()
    }

    private fun main() {
        binding.apply {
            presenter.callFoodOperations()
        }
    }

    override fun loadFoodList(foodList: MutableList<FoodEntity>) {
        binding.apply {
            layoutHideFav.visibility = View.VISIBLE
            layoutEmptyList.visibility = View.GONE

            adapterFavorite.differ.submitList(foodList)
            listItemFavorite.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = adapterFavorite
            }

            adapterFavorite.clickListener {
                val directions = FavoriteFragmentDirections.acHoToDe(it.id)
                findNavController().navigate(directions)
            }
        }
    }

    override fun emptyList() {
        binding.apply {
            layoutHideFav.visibility = View.GONE
            layoutEmptyList.visibility = View.VISIBLE
            includedEmptyItems.imgEmptyOrInternet.setImageResource(R.drawable.empty)
        }
    }

    override fun errorMessage(str: String) {
        Toast.makeText(requireContext(), "$str", Toast.LENGTH_SHORT).show()
    }
}