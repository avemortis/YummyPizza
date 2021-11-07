package com.example.yummypizza

import android.app.Application
import android.content.Context
import com.example.yummypizza.utils.injections.AppComponent
import com.example.yummypizza.utils.injections.DaggerAppComponent

class YummyPizzaApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}

val Context.appComponent: AppComponent
get() = when (this) {
    is YummyPizzaApplication -> appComponent
    else -> this.applicationContext.appComponent
}