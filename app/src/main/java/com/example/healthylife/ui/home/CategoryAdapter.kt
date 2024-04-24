package com.example.healthylife.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthylife.R
import com.example.healthylife.model.CategoryMealList


class CategoryAdapter(
    private val categories: List<CategoryMealList.Category>,
    val clickListener: (CategoryMealList.Category) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val categoryImage: ImageView = itemView.findViewById(R.id.imgCategory)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.categort_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        holder.categoryName.text = category.strCategory

        holder.itemView.setOnClickListener{
            clickListener.invoke(category)
        }

        // Resmi yükle (Glide kullanarak)
        Glide.with(holder.itemView)
            .load(category.strCategoryThumb) // Kategori resim URL'si
            .into(holder.categoryImage)
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}

