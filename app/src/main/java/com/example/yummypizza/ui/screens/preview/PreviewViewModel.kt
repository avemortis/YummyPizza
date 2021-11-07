package com.example.yummypizza.ui.screens.preview

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.yummypizza.data.database.PizzaDatabaseRepository
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.ui.screens.menu.item.MenuItemBottomSheet
import javax.inject.Inject

class PreviewViewModel @Inject constructor() : ViewModel() {
    lateinit var bundle: Bundle
    val index get() = bundle.getInt(MenuItemBottomSheet.TAG)

    fun getSinglePizza() = PizzaDatabaseRepository.getSinglePizza(index)
}