package com.example.healthylife.ui.favorite


import android.icu.text.CaseMap.Title
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthylife.model.MealList
import com.example.healthylife.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID


class FavoriteViewModel : ViewModel() {
    private val _favMealList = MutableLiveData<List<MealList.Meal>>()
    private val  favMealList : LiveData<List<MealList.Meal>> = _favMealList


    // Favori yemekleri listeye ekleyelim
    fun addMeal(title: String, imageView: String){
        val currentList = _favMealList.value.orEmpty().toMutableList()




        // Yeni yemek ögesi
        val newList = MealList.Meal( idMeal = UUID.randomUUID().toString(), strMeal = title, strMealThumb = imageView)
        // Yeni favori listesine ekleyelim
        currentList.add(newList)
        // Güncellenmiş favori yemekler listesini  viewmodele aktaralım
        _favMealList.value = currentList
    }
}





