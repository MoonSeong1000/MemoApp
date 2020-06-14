package com.example.memoapp.config.di

import androidx.room.Room
import com.example.memoapp.db.AppDatabase
import com.example.memoapp.db.CategoryRepository
import com.example.memoapp.db.MemoRepository
import com.example.memoapp.db.source.local.CategoryLocalDataSource
import com.example.memoapp.db.source.local.MemoLocalDataSource
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

    single {
        MemoLocalDataSource.getInstance(get(), get<AppDatabase>().memoDao())
    }
}

val repositoryModule = module {
    single { CategoryRepository(get())}
    single {MemoRepository(get())}
}

val diModules = listOf(
    asyncExecutorModule,
    appDataBaseModule,
    localDataSourceModule,
    repositoryModule
)