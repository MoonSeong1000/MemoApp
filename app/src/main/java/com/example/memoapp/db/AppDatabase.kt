package com.example.memoapp.db

import androidx.room.Database
import com.example.memoapp.db.dao.CategoryDao
import com.example.memoapp.db.dao.MemoDao
import com.example.memoapp.db.entity.Category
import com.example.memoapp.db.entity.Memo

@Database(entities = [Memo::class, Category::class], version = 1)
abstract class AppDatabase {
    abstract fun memoDao(): MemoDao
    abstract fun categoryDao(): CategoryDao
}