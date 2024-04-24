package com.example.healthylife.model


data class CategoryMealList(
    val categories: List<Category>
) {
    data class Category(
        val idCategory: String,
        val strCategory: String,
        val strCategoryDescription: String,
        val strCategoryThumb: String
    )

    companion object {
        // Eşlik eden nesne (companion object) içinde başlatıcı (constructor) veya
        // başka özellikler ekleyebilirsiniz.
        fun create(categories: List<Category>): CategoryMealList {
            return CategoryMealList(categories)
        }
    }

}