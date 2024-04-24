package com.example.healthylife.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthylife.R
import com.example.healthylife.model.FrenchPopularMealList

class MostPopularAdapter(
    private var meals: List<FrenchPopularMealList.FrenchPopularMeal>,
    private val clickListener: (FrenchPopularMealList.FrenchPopularMeal) -> Unit
) : RecyclerView.Adapter<MostPopularAdapter.MostPopularViewHolder>() {

    inner class MostPopularViewHolder(itemView: View,
      val clickListener: (FrenchPopularMealList.FrenchPopularMeal) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        val popularName: TextView = itemView.findViewById(R.id.mostTextView)
        val popularImage: ImageView = itemView.findViewById(R.id.mostImage)



        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedMeal = meals[position]
                    clickListener(clickedMeal)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
        return MostPopularViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.mostpopular_item,
                parent,
                false
            ),
            clickListener
        )
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {
        val popular = meals[position]

        holder.popularName.text = popular.strMeal

        // Glide kullanarak resim yükleyelim
        Glide.with(holder.itemView)
            .load(popular.strMealThumb)
            .into(holder.popularImage)



        // İç boşlukları ayarla
        val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
        layoutParams.setMargins(16, 8, 16, 8) // İstediğiniz boşlukları belirleyin
        holder.itemView.layoutParams = layoutParams

    }
    fun setData(newData: List<FrenchPopularMealList.FrenchPopularMeal>) {
        meals = newData
        notifyDataSetChanged()
    }
}
