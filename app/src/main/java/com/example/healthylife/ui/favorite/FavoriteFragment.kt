package com.example.healthylife.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.healthylife.databinding.FragmentFavoriteBinding
import com.example.healthylife.model.Meal


class FavoriteFragment : Fragment() {

    private val favoriteViewModel : FavoriteViewModel  by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       val binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        favoriteAdapter = FavoriteAdapter{ clicked->
            // Burda tıklanan ögenin verisini işlemem lazım

        }
        binding.recyclerviewFavorites.adapter = favoriteAdapter
        favoriteViewModel.meals.observe(viewLifecycleOwner){
            favoriteAdapter.clickListener
        }


    }



}