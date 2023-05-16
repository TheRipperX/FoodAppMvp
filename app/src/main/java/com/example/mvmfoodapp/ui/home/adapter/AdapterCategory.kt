package com.example.mvmfoodapp.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mvmfoodapp.R
import com.example.mvmfoodapp.data.model.home.ResponseCategory
import com.example.mvmfoodapp.databinding.ItemCategoryListBinding
import javax.inject.Inject

class AdapterCategory @Inject constructor(): RecyclerView.Adapter<AdapterCategory.ViewHolderCategory>() {

    private lateinit var binding: ItemCategoryListBinding
    private var positionAdapter = -1

    inner class ViewHolderCategory: RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun setData(items: ResponseCategory.Category) {

            binding.apply {
                imgCategoryItems.load(items.strCategoryThumb){
                    crossfade(true)
                    crossfade(500)
                    error(R.drawable.notfound)
                }
                txtCategoryItem.text = items.strCategory
            }

            binding.root.setOnClickListener {
                positionAdapter = adapterPosition
                notifyDataSetChanged()
                itemClickAdapter?.let {click ->
                    click(items)
                }
            }
            if (adapterPosition == positionAdapter) {
                binding.root.setBackgroundResource(R.drawable.bg_category_selected)
            }else{
                binding.root.setBackgroundResource(R.drawable.items_bg_sh_category)
            }
        }
    }

    private var itemClickAdapter: ((ResponseCategory.Category) -> Unit)? = null

    fun onItemClick(listener : (ResponseCategory.Category) -> Unit) {
        itemClickAdapter = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCategory {
        val view = LayoutInflater.from(parent.context)
        binding = ItemCategoryListBinding.inflate(view, parent, false)
        return ViewHolderCategory()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolderCategory, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)

    }

    private val differCallBack = object: DiffUtil.ItemCallback<ResponseCategory.Category>() {

        override fun areItemsTheSame(
            oldItem: ResponseCategory.Category,
            newItem: ResponseCategory.Category
        ): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(
            oldItem: ResponseCategory.Category,
            newItem: ResponseCategory.Category
        ): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

}