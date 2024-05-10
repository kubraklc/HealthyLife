package com.example.healthylife.ui.favorite


import android.icu.text.CaseMap.Title
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthylife.model.Meal
import com.example.healthylife.model.MealList
import com.example.healthylife.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID


class FavoriteViewModel : ViewModel() {
    private val _favMealList = MutableLiveData<List<Meal>>()
    val favMealList: LiveData<List<Meal>> = _favMealList

    // Favori yemekleri listeye ekleyelim ve güncellenmiş listeyi döndürelim
    fun addMeal(title: String, img: String): LiveData<List<Meal>> {
        val currentList = _favMealList.value.orEmpty().toMutableList()

        // Yeni yemek ögesi
        val newList = Meal(
            idMeal = UUID.randomUUID().toString(),
            strMeal = title,
            strMealThumb = img
        )
        // Yeni favori listesine ekleyelim
        currentList.add(newList)
        // Güncellenmiş favori yemekler listesini view modele aktaralım
        _favMealList.value = currentList
        // Güncellenmiş listeyi geri döndürelim
        return _favMealList
    }
}



