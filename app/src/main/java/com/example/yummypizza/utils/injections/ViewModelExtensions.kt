package com.example.yummypizza.utils.injections

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ViewModelExtensions {
    inline fun <reified T : ViewModel> Fragment.injectViewModel(factory: ViewModelProvider.Factory) =
        androidx.lifecycle.ViewModelProvider(this, factory)[T::class.java]
}