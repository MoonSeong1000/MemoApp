package com.example.memoapp.db

import com.example.memoapp.db.entity.Category

interface CategoryDataSource {
    fun getAllCategories(callback: (memo:List<Category>)->Unit)

    fun getCategory(categoryId:String, callback:(memo:Category?)->Unit)

    fun saveCategory(category: Category)

    fun modifyCategory(category: Category)

    fun deleteCategory(categoryId: String)

    fun deleteAllCategories()
}