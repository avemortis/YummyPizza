package com.example.yummypizza.utils.injections

import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.data.entities.PizzaPicture
import com.example.yummypizza.ui.screens.preview.PreviewFragment
import com.example.yummypizza.utils.injections.fragments.FragmentBuildersModule
import com.example.yummypizza.utils.injections.viewmodels.ViewModelFactory
import com.example.yummypizza.utils.injections.viewmodels.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ViewModelModule::class]
)
interface AppComponent {
    fun getPizzaService(): PizzaService

    fun viewModelFactory(): ViewModelFactory

    //fun pictureEntity(): PizzaPicture
}