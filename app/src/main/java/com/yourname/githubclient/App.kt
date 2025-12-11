package com.yourname.githubclient

import android.app.Application
import com.yourname.githubclient.di.ServiceLocator

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(applicationContext)
    }
}
