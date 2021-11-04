package com.example.yummypizza

import android.app.Application
import android.content.Context
import com.example.yummypizza.utils.injections.components.DaggerPizzaServiceComponent
import com.example.yummypizza.utils.injections.components.PizzaServiceComponent

class YummyPizzaApplication : Application() {
    lateinit var pizzaServiceComponent: PizzaServiceComponent

    override fun onCreate() {
        super.onCreate()
        pizzaServiceComponent = DaggerPizzaServiceComponent.create()
    }
}

val Context.appComponent: PizzaServiceComponent
get() = when (this) {
    is YummyPizzaApplication -> pizzaServiceComponent
    else -> this.applicationContext.appComponent
}