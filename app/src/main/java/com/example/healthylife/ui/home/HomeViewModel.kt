package com.example.healthylife.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthylife.model.CategoryMealList
import com.example.healthylife.model.FrenchPopularMealList
import com.example.healthylife.model.MealList
import com.example.healthylife.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private  var randomMealLiveData: MutableLiveData<MealList.Meal> = MutableLiveData()
    private val categoryListLiveData: MutableLiveData<CategoryMealList> = MutableLiveData()
    private val mostPopularLiveData : MutableLiveData<FrenchPopularMealList> = MutableLiveData()


    fun getMealRandom(){
        RetrofitInstance.api.getMealRandom().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() !=null){
                    val randomrecipe : MealList.Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomrecipe
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HOME fragment", t.message.toString())
            }

        })
    }

    fun getCategoryMealList() {
        viewModelScope.launch {
            RetrofitInstance.api.getCategoryMealList().enqueue(object : Callback<CategoryMealList>{
                override fun onResponse(
                    call: Call<CategoryMealList>,
                    response: Response<CategoryMealList>,
                ) {
                    if (response.isSuccessful){
                        categoryListLiveData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<CategoryMealList>, t: Throwable) {
                    Log.d("homefragment", t.message.toString())
                }

            })
        }
    }

    fun getPopularFrenchMeal(categoryName: String) {
        RetrofitInstance.api.getPopularFrenchMeal(categoryName).enqueue(object : Callback<FrenchPopularMealList> {
            override fun onResponse(
                call: Call<FrenchPopularMealList>,
                response: Response<FrenchPopularMealList>,
            ) {
                if (response.isSuccessful) {
                    // Request'in url'ini loglayan kod
                    // Response doğru gelmiyorsa önce bunu kontrol et
                    //Log.d("REQUEST URL", response.raw().request().url().toString())
                    val mealList: FrenchPopularMealList? = response.body()
                    mostPopularLiveData.value = mealList
                } else {
                    // Sunucu tarafından gönderilen hata mesajını alalım
                    val errorBody = response.errorBody()?.string()
                    Log.e("HomeviewModel", "API Hatası: $errorBody")

                }
            }

            override fun onFailure(call: Call<FrenchPopularMealList>, t: Throwable) {
                Log.e("HomeviewModel", t.message.toString())
            }
        })
    }




    fun observeRandomMealLiveData():LiveData<MealList.Meal>{
        return randomMealLiveData
    }

    fun observeCategoryListLiveData(): LiveData<CategoryMealList> {
        return categoryListLiveData
    }

    fun observeMostPopularLiveData():LiveData<FrenchPopularMealList>{
        return  mostPopularLiveData
    }

}


