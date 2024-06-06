package com.example.healthylife.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.healthylife.R
import com.example.healthylife.databinding.FragmentRecipeBinding
import com.example.healthylife.model.firebasemodels.FavoriteMealFirebase
import com.example.healthylife.ui.favorite.FavoriteAdapter
import com.example.healthylife.ui.favorite.FavoriteViewModel
import com.example.healthylife.ui.home.HomeFragment.Companion.CATEGORY_NAME
import com.example.healthylife.ui.home.HomeFragment.Companion.MEAL_INSTRUCTION
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter : FavoriteAdapter
    private lateinit var databaseReference: DatabaseReference
    companion object {
        const val MEAL_ID = "com.example.easyfood.fragments.idMeal"
        const val MEAL_NAME = "com.example.easyfood.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.easyfood.fragments.thumbMeal"
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.imageFavorite.setOnClickListener {
           val toastView = inflater.inflate(R.layout.new_toast_message, null)
           toastView.findViewById<TextView>(R.id.toastText).text = "Favorilere Eklendi"
           toastView.findViewById<ImageView>(R.id.toastText)
           val toast = Toast(requireContext())
           toast.duration = Toast.LENGTH_LONG
           toast.view = toastView
           toast.show()

            val mealName = arguments?.getString(MEAL_NAME)
            val mealThumb = arguments?.getString(MEAL_THUMB)
            val bundle = Bundle().apply {
                putString("title", mealName)
                putString("img", mealThumb)
            }

            // Firebasedeki veritabanına erişim sağlayalım
            databaseReference = FirebaseDatabase.getInstance("https://healthylife-b03db-default-rtdb.europe-west1.firebasedatabase.app").getReference("favorites-recipes")
            val mealModel = FavoriteMealFirebase(userId = "1", idMeal = arguments?.getString(MEAL_ID).toString(), strMealThumb = arguments?.getString(
                MEAL_THUMB).toString(), strMeal = arguments?.getString(MEAL_NAME).toString())
            databaseReference.child(arguments?.getString(MEAL_ID).toString()).setValue(mealModel).addOnSuccessListener {
            }

            try {
                // Favori yemeği ekleyip güncellenmiş listeyi alalım
                favoriteViewModel.addMeal(id = MEAL_ID, title = "Yemek Başlığı", img = "drawable/splashimg.jpg")
                    .observe(viewLifecycleOwner) { updatedList ->
                        // Güncellenmiş listeyi almak için burada gerekli işlemleri yapabilirsiniz
                        favoriteAdapter.submitList(updatedList)
                    }

            } catch (e: Exception) {
                Log.e("Error", "addMeal fonksiyonunda hata oluştu: ${e.message}")
            }
        }
       return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Argumentleri al
        val mealId = arguments?.getString(MEAL_ID)
        val mealName = arguments?.getString(MEAL_NAME)
        val mealThumb = arguments?.getString(MEAL_THUMB)
        val mealInstruction = arguments?.getString(MEAL_INSTRUCTION)
        val mealCategoryInfo = arguments?.getString(CATEGORY_NAME)

        // Verileri gösterme fonksiyonunu çağır
        displayMealDetails(mealId, mealName, mealThumb, mealInstruction, mealCategoryInfo)
    }

    private fun displayMealDetails(
        mealId: String?,
        mealName: String?,
        mealThumb: String?,
        mealInstruction: String?,
        mealCategoryInfo: String?,
    ) {
        if (mealId != null && mealName != null && mealThumb != null && mealInstruction != null && mealCategoryInfo != null) {
            binding.descMealId.text = "Meal ID: $mealId"
            binding.descTitle.text = mealName
            binding.categoryInstruction.text = mealInstruction
            binding.categoryInfo.text = mealCategoryInfo

            Glide.with(requireContext())
                .load(mealThumb)
                .into(binding.descimgView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}








