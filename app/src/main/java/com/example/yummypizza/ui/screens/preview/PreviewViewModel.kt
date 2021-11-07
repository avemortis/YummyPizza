package com.example.yummypizza.ui.screens.preview

import androidx.lifecycle.ViewModel
import com.example.yummypizza.data.entities.PizzaEntity
import javax.inject.Inject

class PreviewViewModel @Inject constructor() : ViewModel() {
    val pizzaEntity = PizzaEntity(
        1,
        "God, I love this pizza",
        300,
        "2",
        listOf(
            "https://www.delonghi.com/Global/recipes/multifry/3.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg"
        ) as MutableList<String>
    )
}