package com.example.healthylife.model

data class FrenchPopularMealList(
    val meals: List<FrenchPopularMeal>
) {
    data class FrenchPopularMeal(
        val idMeal: String,
        val strMeal: String,
        val strMealThumb: String
    )
}




