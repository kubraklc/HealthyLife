package com.example.healthylife.ui.favorite

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthylife.R
import com.example.healthylife.model.Meal


class FavoriteAdapter(
        private var favoritemeals: List<Meal>,
        val clickListener: (List<Meal>) -> Unit,
        val context: Context,
        val deleteListener: (Meal) -> Unit
    ) :  RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
       val mealName: TextView = itemView.findViewById(R.id.favText)
       val mealThumb: ImageView = itemView.findViewById(R.id.favImg)
       val mealDelete : ImageView = itemView.findViewById(R.id.favDelete)
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
       val meal = favoritemeals[position]
        holder.mealName.text = meal.strMeal
        holder.mealDelete.setImageDrawable(context.getDrawable(R.drawable.ic_delete))

        holder.itemView.setOnClickListener {
            clickListener(favoritemeals) // Favori yemeği tıklama olayını dinleyiciye iletin
        }

        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.mealThumb)

        holder.mealDelete.setOnClickListener {
            showAlertDialog(meal)
        }
    }

    fun submitList(updatedList: List<Meal>?) {
       favoritemeals = updatedList.orEmpty()
        notifyDataSetChanged() // Veri seti değiştiğinde RecyclerView'i güncellemek için kullanıyorum
    }

    private fun showAlertDialog(meal: Meal) {
        val delete = AlertDialog.Builder(context)
        delete.setMessage("Silmek istediğinize emin misiniz?")
        delete.setPositiveButton("Evet") { dialogInterface, i ->
            deleteListener(meal)
        }
        delete.setNegativeButton("Hayır", null)
        val showDelete = delete.create()
        showDelete.show()
    }

}