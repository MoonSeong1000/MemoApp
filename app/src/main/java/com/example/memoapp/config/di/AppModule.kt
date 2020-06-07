package com.example.memoapp.config.di

import androidx.room.Room
import com.example.memoapp.db.AppDatabase
import com.example.memoapp.db.local.CategoryLocalDataSource
import com.example.memoapp.util.AsyncExecutor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val asyncExecutorModule = module {
    single { AsyncExecutor() }
}

val appDataBaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database").build()
    }
}

val localDataSourceModule = module {
    single {
        CategoryLocalDataSource.getInstance(get(), get<AppDatabase>().categoryDao())
    }
}

val diModules = listOf(
    asyncExecutorModule,
    appDataBaseModule,
    localDataSourceModule
)