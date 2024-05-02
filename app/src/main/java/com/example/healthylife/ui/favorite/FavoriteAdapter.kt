package com.example.healthylife.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthylife.R
import com.example.healthylife.model.Meal


class FavoriteAdapter(
    private var favoritemeals: List<Meal>,
    val clickListener: (List<Meal>) -> Unit
) :  RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
       val title: TextView = itemView.findViewById(R.id.favoritesText)
       val img: ImageView = itemView.findViewById(R.id.imageFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
       return FavoriteViewHolder(
           LayoutInflater.from(parent.context).inflate(
               R.layout.fragment_favorite_item,
               parent,
               false
           )
       )
    }

    override fun getItemCount(): Int {
       return favoritemeals.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
       val meal= favoritemeals[position]
        holder.title.text = meal.strMeal

        holder.itemView.setOnClickListener{
            clickListener
        }


        Glide.with(holder.itemView)
            .load(R.drawable.splashimg2)
            .into(holder.img)



    }

}