package com.example.memoapp.config

import android.app.Application
import com.example.memoapp.config.di.diModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MemoApplication)
            modules(diModules)
        }
    }

}
