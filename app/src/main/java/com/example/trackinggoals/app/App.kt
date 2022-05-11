package com.example.trackinggoals.app

import android.app.Application
import com.example.trackinggoals.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            fragmentFactory()
            modules(
                listOf(
                    databaseModules,
                    apiModules,
                    repositoryModules,
                    applicationModules,
                    viewModelsModules
                )
            )
        }
    }
}