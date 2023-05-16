package com.example.mvmfoodapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mvmfoodapp.R
import com.example.mvmfoodapp.data.model.home.ResponseFoodList
import com.example.mvmfoodapp.databinding.ItemFoodListBinding
import javax.inject.Inject

class AdapterFoodList @Inject constructor(): RecyclerView.Adapter<AdapterFoodList.ViewHolderFood>() {

    private lateinit var binding: ItemFoodListBinding

    inner class ViewHolderFood: RecyclerView.ViewHolder(binding.root) {
        fun setData(items: ResponseFoodList.Meal) {
            binding.apply {
                imgFoodList.load(items.strMealThumb){
                    crossfade(false)
                    crossfade(500)
                    error(R.drawable.notfound)
                }

                if (items.strMeal.isNullOrEmpty()){
                    txtFoodListTitle.visibility = View.GONE
                }else{
                    txtFoodListTitle.visibility = View.VISIBLE
                    txtFoodListTitle.text = items.strMeal
                }

                if (items.strCategory.isNullOrEmpty()){
                    txtFood.visibility = View.GONE
                }else{
                    txtFood.visibility = View.VISIBLE
                    txtFood.text = items.strCategory
                }

                if (items.strArea.isNullOrEmpty()){
                    txtFoodArea.visibility = View.GONE
                }else{
                    txtFoodArea.visibility = View.VISIBLE
                    txtFoodArea.text = items.strArea
                }

                if (items.strSource.isNullOrEmpty()){
                    txtFoodSite.visibility = View.GONE
                }else{
                    txtFoodSite.visibility = View.VISIBLE
                }

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFood {
        val view = LayoutInflater.from(parent.context)
        binding = ItemFoodListBinding.inflate(view, parent, false)
        return ViewHolderFood()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolderFood, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    private val differCallBack = object: DiffUtil.ItemCallback<ResponseFoodList.Meal>() {

        override fun areItemsTheSame(
            oldItem: ResponseFoodList.Meal,
            newItem: ResponseFoodList.Meal
        ): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(
            oldItem: ResponseFoodList.Meal,
            newItem: ResponseFoodList.Meal
        ): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

}