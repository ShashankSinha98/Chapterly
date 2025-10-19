package com.lucifer.chapterly

import android.app.Application
import com.lucifer.chapterly.di.initKoin
import org.koin.android.ext.koin.androidContext

class BookApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BookApplication)
            // Can add Android specific modules here if needed
        }
    }
}