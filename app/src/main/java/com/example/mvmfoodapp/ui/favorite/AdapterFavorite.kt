package com.example.mvmfoodapp.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mvmfoodapp.data.db.FoodEntity
import com.example.mvmfoodapp.data.model.home.ResponseFoodList
import com.example.mvmfoodapp.databinding.ItemFoodListBinding
import javax.inject.Inject

class AdapterFavorite @Inject constructor(): RecyclerView.Adapter<AdapterFavorite.ViewHolderFavorite>() {

    private lateinit var binding: ItemFoodListBinding

    inner class ViewHolderFavorite: RecyclerView.ViewHolder(binding.root) {
        fun getData(item: FoodEntity) {
            binding.apply {
                imgFoodList.load(item.foodImage)
                txtFoodListTitle.text = item.foodName
                txtFood.visibility = View.GONE
                txtFoodArea.visibility = View.GONE
                txtFoodSite.visibility = View.GONE
            }
            binding.root.setOnClickListener {
                clickItems?.let {food->
                    food(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFavorite {
        binding = ItemFoodListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderFavorite()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolderFavorite, position: Int) {
        holder.getData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    private var clickItems: ((FoodEntity) -> Unit)? = null

    fun clickListener(listener: (FoodEntity) -> Unit) {
        clickItems = listener
    }


    private val differFavorite = object: DiffUtil.ItemCallback<FoodEntity>() {
        override fun areItemsTheSame(
            oldItem: FoodEntity,
            newItem: FoodEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FoodEntity,
            newItem: FoodEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    val differ = AsyncListDiffer(this, differFavorite)
}