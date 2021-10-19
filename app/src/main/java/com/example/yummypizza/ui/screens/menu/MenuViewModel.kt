package com.example.yummypizza.ui.screens.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.battisq.pizzamarket.PizzaDatabase
import com.battisq.pizzamarket.PizzaEntity

class MenuViewModel : ViewModel() {
    val menu : MutableList<PizzaEntity> = mutableListOf()
    val menuLiveData: MutableLiveData<List<PizzaEntity>> = MutableLiveData(mutableListOf())

    init {
        getMenu()
    }

    private fun getMenu(){
        menuLiveData.apply {
            value = PizzaDatabase.pizzaDao.getAll()
        }
    }

    fun searchByMenu(string: String?){
        val correct : MutableList<PizzaEntity> = mutableListOf()
        if (string!=null){
            PizzaDatabase.pizzaDao.getAll().forEach{
                if((it.name.indexOf(string, 0) != -1)
                    ||(it.description.indexOf(string, 0) != -1)){
                    correct.add(it)
                }
            }
        }

        menuLiveData.value = correct
    }
}