package com.example.yummypizza.utils.injections.components

import android.content.Context
import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.utils.injections.ViewModelFactory
import com.example.yummypizza.utils.injections.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ViewModelModule::class]
)
interface PizzaServiceComponent {

/*    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(context: Context): Builder

        fun build(): PizzaServiceComponent
    }*/

    fun getPizzaService(): PizzaService

    fun viewModelFactory(): ViewModelFactory
}