package com.example.healthylife.ui.favorite

import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.healthylife.R
import com.example.healthylife.databinding.FragmentFavoriteBinding
import com.example.healthylife.model.Meal
import com.example.healthylife.ui.detail.RecipeFragment
import com.example.healthylife.ui.home.HomeFragment.Companion.MEAL_NAME
import com.example.healthylife.ui.home.HomeFragment.Companion.MEAL_THUMB


class FavoriteFragment : Fragment() {
    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root

        val title = arguments?.getString(MEAL_NAME)
        val img = arguments?.getString(MEAL_THUMB)
        if (title != null && img != null) {
            // Verileri kullanarak RecyclerView'ı güncelleme işlemleri yapabilirsiniz
            showFavoriteMeal(title, img)
        }
        // RecyclerView'ı oluştur ve ayarla
        favoriteAdapter = FavoriteAdapter(emptyList()) { clickedMeal ->
            // Favori yemeğe tıklanma durumunda yapılacak işlemler buraya yazalım
            //  tıklanan yemeğin detaylarını göstercez
        }
        binding.recyclerviewFavorites.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = favoriteAdapter
        }
        return view
    }

    private fun showFavoriteMeal(title: String, img: String) {
        // Favori yemeği gösterme işlemleri burada yapılabilir
        binding.favoritesText.text = title
         Glide.with(this)
             .load(img)
             .into(binding.imageFavorite)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
