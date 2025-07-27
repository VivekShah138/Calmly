package com.example.calmly

import android.app.Application
import com.example.calmly.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CalmlyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CalmlyApp)
            modules(appModule)
        }
    }
}