package com.example.healthylife.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthylife.R
import com.example.healthylife.databinding.FragmentHomeBinding
import com.example.healthylife.model.CategoryMealList
import com.example.healthylife.model.MealList.Meal
import com.example.healthylife.ui.detail.RecipeFragment
import com.google.firebase.database.DatabaseReference


class HomeFragment : Fragment() {

    private var  _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter2 : MostPopularAdapter
    private lateinit var databaseReference: DatabaseReference

    companion object{
        const val MEAL_ID= "com.example.easyfood.fragments.idMeal"
        const val MEAL_NAME ="com.example.easyfood.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.easyfood.fragments.thumbMeal"
        const val MEAL_INSTRUCTION = "com.example.easyfood.fragments.mealInstruction"
        const val CATEGORY_NAME = "com.example.easyfood.fragments.categoryName"
        const val MEAL_LINK = "com.example.easyfood.fragments.mealLink"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container ,false)
        val view = binding.root
        // searchview bulma ve kullanma
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularRecyclerview()

        homeViewModel.getMealRandom()
        observeRandomMealLiveData()


        homeViewModel.getCategoryMealList()
        observeCategoryListLiveData()

        homeViewModel.getPopularFrenchMeal(categoryName= "French")
        observeMostPopularLiveData()

    }



    private fun observeRandomMealLiveData() {
       homeViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, object : Observer<Meal>{
           override fun onChanged(t: Meal?) {
               Glide.with(this@HomeFragment)
                   .load(t!!.strMealThumb)
                   .into(binding.randomRecipe)

               // Random yemek görüntüsüne tıklama işlemi
               binding.randomRecipe.setOnClickListener {

                   // Random yemek bilgilerini alalım
                   val randomRecipe = homeViewModel.observeRandomMealLiveData().value
                   // Null kontrolü yapalım
                   if (randomRecipe != null) {
                       // Bundle oluşturun
                       val bundle = Bundle().apply {
                           putString(MEAL_ID, randomRecipe.idMeal)
                           putString(MEAL_NAME, randomRecipe.strMeal)
                           putString(MEAL_THUMB, randomRecipe.strMealThumb)
                           putString(CATEGORY_NAME, randomRecipe.strCategory)
                           putString(MEAL_LINK, randomRecipe.strYoutube)
                           putString(MEAL_INSTRUCTION, randomRecipe.strInstructions)
                       }

                       // RecipeFragment'ı oluşturun ve Bundle'ı ekleyelim
                       val recipeFragment = RecipeFragment()
                       recipeFragment.arguments = bundle
                       requireActivity().supportFragmentManager.beginTransaction()
                           .replace(R.id.frameLayout, recipeFragment)
                           .addToBackStack(null)
                           .commit()

                   }else Log.e("No meal", "random recipe null")
               }
           }
       })
    }

  private fun observeCategoryListLiveData() {
      homeViewModel.observeCategoryListLiveData().observe(viewLifecycleOwner, Observer { categoryMealList ->
          val recyclerView = view?.findViewById<RecyclerView>(R.id.rec_view_categories)
          val adapter = CategoryAdapter(categoryMealList.categories) { clickedCategory ->
              clickCategory(clickedCategory)
          }

          val gridLayoutManager = GridLayoutManager(requireContext(), 3)
          recyclerView?.layoutManager = gridLayoutManager
          recyclerView?.adapter = adapter
      })
  }

  private fun clickCategory(clickedCategory: CategoryMealList.Category) {
      // Buraya category list tıklama olayı yazılacak
  }

  private fun preparePopularRecyclerview() {
      adapter2 = MostPopularAdapter(
          meals = emptyList(), // Başlangıçta boş bir liste
          clickListener = { clickedMeal ->
              // Tıklanıldığında yapılacak işlemleri burada tanımlayabiliriz.
          }
      )

      binding.recPopularMeal.apply {
          layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
          adapter = adapter2
      }
  }

  private fun observeMostPopularLiveData() {
      homeViewModel.observeMostPopularLiveData().observe(viewLifecycleOwner, Observer { mealList ->
          if (mealList != null && mealList.meals != null) {
              adapter2.setData(mealList.meals)
          } else{
              Log.d("homefragment", "Veri kümesi veya meals null.")
          }
      })
  }

  override fun onDestroyView() {
      super.onDestroyView()
      // Binding null yapma
      _binding = null
  }
}





