package com.example.yummypizza.data.components

import com.example.yummypizza.data.api.PizzaService
import dagger.Component

@Component
interface PizzaServiceComponent {
    fun getPizzaService(): PizzaService
}