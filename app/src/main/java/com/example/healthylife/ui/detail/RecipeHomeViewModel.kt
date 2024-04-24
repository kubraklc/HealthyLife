package com.example.healthylife.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthylife.model.MealList
import com.example.healthylife.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeHomeViewModel : ViewModel() {

    private val recipeDetailsLiveData = MutableLiveData<MealList.Meal>()


    fun getRecipeMeal(mealName : String) {
        RetrofitInstance.api.getRecipeMeal(mealName).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    Log.d("Meal Success", "Request successful. Response code: ${response.code()}")
                    // Diğer işlemleri buraya ekleyin
                    val meals = response.body()?.meals
                    if (meals != null && meals.isNotEmpty()) {
                       recipeDetailsLiveData.value = meals[0]
                        Log.d("Meal ADDED", "Meal details added to LiveData")
                    } else {
                        Log.e("Meal No", "No meals in the response.")
                    }
                } else {
                    Log.e("Retrofit", "Request failed. Response code: ${response.code()}")
                    // Diğer hata işlemlerini buraya ekleyin
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("Meal Fail", "Failed to load recipe detail", t)
            }
        })
    }

    fun observeRecipeDetailLiveData(mealName: String): LiveData<MealList.Meal> {
        getRecipeMeal(mealName)
        return recipeDetailsLiveData
    }
}
