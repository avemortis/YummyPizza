package com.example.yummypizza.ui.screens.menu.item

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummypizza.data.entities.PizzaEntity

class MenuItemBottomSheetViewModel : ViewModel() {
    lateinit var bundle: Bundle
    private val index get() = bundle.getInt(MenuItemBottomSheet.TAG)
    private val pizzaLiveData: MutableLiveData<PizzaEntity> = MutableLiveData()

    fun getPizzaLiveData() : LiveData<PizzaEntity>{
        try {
            pizzaLiveData.apply {
                //value = PizzaDatabase.pizzaDao.getById(index)
            }
        } catch (e : Exception){
            throw (IllegalStateException("No bundle or bundle das not contain index"))
        }

        return pizzaLiveData
    }
}