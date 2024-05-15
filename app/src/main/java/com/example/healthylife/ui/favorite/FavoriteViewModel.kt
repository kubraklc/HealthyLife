package com.example.healthylife.ui.favorite


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthylife.model.Meal
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



