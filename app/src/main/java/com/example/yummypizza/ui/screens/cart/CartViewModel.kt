package com.example.yummypizza.ui.screens.cart

import androidx.lifecycle.ViewModel
import com.example.yummypizza.data.entities.CartItem

class CartViewModel : ViewModel() {
    lateinit var cart : MutableList<CartItem>
}