package com.example.yummypizza.utils.injections

import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.utils.injections.viewmodels.ViewModelFactory
import com.example.yummypizza.utils.injections.viewmodels.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ViewModelModule::class]
)
interface AppComponent {

/*    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(context: Context): Builder

        fun build(): PizzaServiceComponent
    }*/

    fun getPizzaService(): PizzaService

    fun viewModelFactory(): ViewModelFactory
}