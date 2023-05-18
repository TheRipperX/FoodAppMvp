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
import com.example.mvmfoodapp.utils.DifferAdapterApp
import javax.inject.Inject

class AdapterFoodList @Inject constructor(): RecyclerView.Adapter<AdapterFoodList.ViewHolderFood>() {

    private lateinit var binding: ItemFoodListBinding

    private var listData = emptyList<ResponseFoodList.Meal>()

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

            binding.root.setOnClickListener {
                itemsClick?.let {click ->
                    click(items)
                }
            }
        }
    }

    private var itemsClick: ((ResponseFoodList.Meal) -> Unit)? = null

    fun setClickItems(listener: (ResponseFoodList.Meal) -> Unit) {
        itemsClick = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFood {
        val view = LayoutInflater.from(parent.context)
        binding = ItemFoodListBinding.inflate(view, parent, false)
        return ViewHolderFood()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolderFood, position: Int) {
        holder.setData(listData[position])
        holder.setIsRecyclable(false)
    }

    fun getItemsData(items: List<ResponseFoodList.Meal>) {
        val classDiffer = DifferAdapterApp(listData, items)
        val diffUtil = DiffUtil.calculateDiff(classDiffer)
        listData = items
        diffUtil.dispatchUpdatesTo(this)
    }

/*    private val differCallBack = object: DiffUtil.ItemCallback<ResponseFoodList.Meal>() {

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

    val differ = AsyncListDiffer(this, differCallBack)*/

}