package com.example.healthylife.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.healthylife.databinding.FragmentFavoriteBinding
import com.example.healthylife.model.Meal
import com.example.healthylife.model.firebasemodels.FavoriteMealFirebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FavoriteFragment : Fragment() {
    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteAdapter: FavoriteAdapter
    private  val favoriteViewModel : FavoriteViewModel by viewModels()
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        // RecyclerView'ı oluştur ve ayarla
        favoriteAdapter = FavoriteAdapter(
            emptyList(),
            clickListener = { meals ->
                // Favori yemeğe tıklanma durumu
                // tıklanan yemeğin detaylarını göstermek ve recipe fragmentına geçmek için işlemler
            },
             requireContext(),
            deleteListener = { meal ->
                deleteFavoriteMeal(meal, databaseReference)

            }
        )

        binding.recyclerviewFavorites.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            binding.recyclerviewFavorites.setLayoutManager(layoutManager)
            adapter = favoriteAdapter
        }

        // ViewModel'den verileri gözlemleyin
        favoriteViewModel.favMealList.observe(viewLifecycleOwner) { mealList ->
            favoriteAdapter.submitList(mealList)
        }

        // Firebase üzerindeki veri tabanı instanceımızı alıyoruz favorites-recipes referansı ile beraber
        databaseReference = FirebaseDatabase.getInstance("https://healthylife-b03db-default-rtdb.europe-west1.firebasedatabase.app").getReference("favorites-recipes")

        // userId = 1 durumuna göre sorgu oluşturuyoruz ki giriş yapmış kullanıcının listesine erişebilelim
        val query = databaseReference.orderByChild("userId").equalTo("1")

        // Sorgumuzu çalıştırıyoruz
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val recipes = mutableListOf<FavoriteMealFirebase>()
                // çoklu halinde data döneceği için liste tipinde geliyor
                // o yüzden foreach ile childrenları dolaşıp sorgumuzun sonucundaki datamızı alıyoruz ve recipes listesine ekliyoruz
                for (snapshot in p0.children) {
                    // tekli recipe datasının getirildiği yer
                    val recipe = snapshot.getValue(FavoriteMealFirebase::class.java)
                    recipe?.let {
                        // recipes listesine ekleme kısmı
                        recipes.add(it)
                    }
                }

                // recipes listesine eklediğimiz veritabanından dönen dataları favoriteviewmodel'e ekliyoruz ki
                // favori datamızı adapter'e bağlayıp listeleyebilelim ekranda
                for (recipe in recipes) {
                    favoriteViewModel.addMeal(recipe.idMeal, recipe.strMeal, recipe.strMealThumb)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

                Log.e("FavoriteFragment", "Database error: ${p0.message}")
            }
        })

        favoriteViewModel.favMealList.observe(viewLifecycleOwner) { updatedList ->
            favoriteAdapter.submitList(updatedList)
        }
        return binding.root
    }

    private fun deleteFavoriteMeal(meal: Meal, reference: DatabaseReference){
        val mealId = meal.idMeal
        val itemReference = reference.child(mealId)
        itemReference.removeValue()
            .addOnSuccessListener {
                // Favori yemek listesinden kaldır
                favoriteViewModel.removeMeal(meal)
                Toast.makeText(context, "Yemek Silindi", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Silme işlemi başarısız oldu", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}







