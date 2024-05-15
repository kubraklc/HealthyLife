package com.example.healthylife.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.healthylife.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {
    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteAdapter: FavoriteAdapter
    private  val favoriteViewModel : FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)


        // RecyclerView'ı oluştur ve ayarla
        favoriteAdapter = FavoriteAdapter(emptyList()) { clickedMeal ->
            // Favori yemeğe tıklanma durumunda yapılacak işlemler buraya yazalım
            //  tıklanan yemeğin detaylarını göstercez ve recipe fragmentta geçicez
        }
        binding.recyclerviewFavorites.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = favoriteAdapter
        }

        // ViewModel'den verileri gözlemleyin
        favoriteViewModel.favMealList.observe(viewLifecycleOwner) { mealList ->
            favoriteAdapter.submitList(mealList)
        }

        val mealName = arguments?.getString("title")
        val mealThumb = arguments?.getString("img")
        if (mealName != null && mealThumb != null) {
            Glide.with(requireContext())
                .load(mealThumb)
                .into(binding.imageFavorite)

            // Verileri kullanarak ViewModel'e ekleme işlemi yapabilirsiniz
            favoriteViewModel.addMeal(mealName, mealThumb)
        }


        favoriteViewModel.favMealList.observe(viewLifecycleOwner) { updatedList ->
            favoriteAdapter.submitList(updatedList)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
