package com.example.memoapp.db.local

import com.example.memoapp.db.CategoryDataSource
import com.example.memoapp.db.dao.CategoryDao
import com.example.memoapp.db.entity.Category
import com.example.memoapp.util.AsyncExecutor
class CategoryLocalDataSource private constructor(
    private val asyncExecutor: AsyncExecutor,
    private val categoryDao: CategoryDao
) : CategoryDataSource {

    override fun getCategories(callback: (categories: List<Category>) -> Unit) {
        asyncExecutor.ioThread.execute {
            val categories = categoryDao.getCategories()
            asyncExecutor.mainThread.execute { callback(categories) }
        }
    }

    override fun getCategory(categoryId: String, callback: (category: Category) -> Unit) {
        asyncExecutor.ioThread.execute {
            val category = categoryDao.getCategoryById(categoryId)
            asyncExecutor.mainThread.execute { callback(category) }
        }
    }

    override fun saveCategory(category: Category) {
        asyncExecutor.ioThread.execute { categoryDao.insertCategory(category) }
    }

    override fun modifyCategory(category: Category) {
        asyncExecutor.ioThread.execute { categoryDao.updateCategory(category) }
    }

    override fun deleteCategory(categoryId: String) {
        asyncExecutor.ioThread.execute { categoryDao.deleteCategoryById(categoryId) }
    }

    companion object {
        private var INSTANCE: CategoryLocalDataSource? = null

        @JvmStatic
        @Synchronized
        fun getInstance(
            asyncExecutor: AsyncExecutor,
            categoryDao: CategoryDao
        ): CategoryLocalDataSource = INSTANCE
            ?: CategoryLocalDataSource(asyncExecutor, categoryDao).apply { INSTANCE = this }
    }

}