package com.example.healthylife.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthylife.model.Meal
import com.example.healthylife.retrofit.RetrofitInstance.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.FieldPosition

class FavoriteViewModel : ViewModel() {
    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> = _meals

    fun fetchMeals() {
        api.getMealRandom().enqueue(object : Callback<List<Meal>> {
            override fun onResponse(call: Call<List<Meal>>, response: Response<List<Meal>>) {
                if (response.isSuccessful) {
                    val mealList = response.body() ?: emptyList()
                    _meals.value = mealList
                }
            }

            override fun onFailure(call: Call<List<Meal>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    fun addMeal(meal: Meal) {
        val currentList = _meals.value.orEmpty().toMutableList()
        currentList.add(meal)
        _meals.value = currentList
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<List<Meal>>) {

}


