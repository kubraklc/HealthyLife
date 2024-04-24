package com.example.healthylife.retrofit

import com.example.healthylife.model.CategoryMealList
import com.example.healthylife.model.FrenchPopularMealList
import com.example.healthylife.model.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getMealRandom(): Call<MealList>

    @GET("categories.php")
    fun getCategoryMealList() : Call<CategoryMealList>

    @GET("lookup.php")
    fun getRecipeMeal(@Query("i") mealName : String) : Call<MealList>

    @GET("filter.php?")
    fun getPopularFrenchMeal(@Query("a") categoryName : String) : Call<FrenchPopularMealList>





}

