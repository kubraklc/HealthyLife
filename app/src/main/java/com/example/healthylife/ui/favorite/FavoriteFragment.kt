package com.example.healthylife.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mealName = arguments?.getString("title", MEAL_NAME)
        val mealThumb = arguments?.getString("img", MEAL_THUMB)
        showFavoriteMeal(mealName, mealThumb)
    }

    private fun showFavoriteMeal(mealName: String?, mealThumb: String?) {
         if (mealName != null && mealThumb !=null){
             binding.favoritesText.text = mealName
             Glide.with(this)
                 .load(mealThumb)
                 .into(binding.imageFavorite)
         }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        //  Favori Fragmentta resime tıklama olayında Recipe fragmenta geçme
        binding.imageFavorite.setOnClickListener{
            val recipeFragment = RecipeFragment()
           recipeFragment.requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, recipeFragment)
                .addToBackStack(null)
                .commit()

        }

        return  binding.root
    }


}