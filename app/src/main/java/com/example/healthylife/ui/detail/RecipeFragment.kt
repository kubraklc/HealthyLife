package com.example.healthylife.ui.detail

import android.os.Bundle
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
import com.example.healthylife.model.Meal
import com.example.healthylife.ui.favorite.FavoriteViewModel
import com.example.healthylife.ui.home.HomeFragment.Companion.CATEGORY_NAME
import com.example.healthylife.ui.home.HomeFragment.Companion.MEAL_ID
import com.example.healthylife.ui.home.HomeFragment.Companion.MEAL_INSTRUCTION
import com.example.healthylife.ui.home.HomeFragment.Companion.MEAL_NAME
import com.example.healthylife.ui.home.HomeFragment.Companion.MEAL_THUMB

@Suppress("DEPRECATION")
class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private  val  favoriteViewModel: FavoriteViewModel by viewModels()


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

          favoriteViewModel.fetchMeals()
            favoriteViewModel.addMeal(meal = Meal(strMeal = String(), strMealThumb = String()))


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
        mealCategoryInfo: String?
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


/*
        mealName = _binding?.descTitle.toString()
        val recipeHomeViewModel = ViewModelProvider(this).get(RecipeHomeViewModel::class.java)
        recipeHomeViewModel.getRecipeMeal(mealName)

        //  onYoutubeClick()
     //   observeRecipeDetailLiveData()  // burası filter olcak sonra
    }

 private fun onYoutubeClick() {
        // YouTube linkini göster
        binding..setOnClickListener {
            if (!youtubeLink.isNullOrBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                startActivity(intent)
            } else {
                // YouTube linki boşsa veya null ise kullanıcıya bir uyarı verilebilir.
                Toast.makeText(requireContext(), "YouTube link not available", Toast.LENGTH_SHORT).show()
            }
        }
    }




    private fun observeRecipeDetailLiveData() {
        recipeHomeViewModel.observeRecipeDetailLiveData(mealName)
            .observe(viewLifecycleOwner, Observer { recipeDetail ->
                // Yemek detayları LiveData'dan alındığında yapılacak işlemler
                updateUIWithRecipeDetail(recipeDetail)
            })
    }

@SuppressLint("ResourceType")
private fun updateUIWithRecipeDetail(recipeDetail: MealList.Meal) {
binding?.apply {
    descMealId.text = "Meal ID: ${recipeDetail.idMeal}"
    descTitle.text = recipeDetail.strMeal
    categoryInstruction.text = recipeDetail.strInstructions
    categoryInfo.text = ":${recipeDetail.strCategory}"

    Glide.with(this@RecipeFragment)
        .load(recipeDetail.strMealThumb)
        .into(descimgView)
}
   */








