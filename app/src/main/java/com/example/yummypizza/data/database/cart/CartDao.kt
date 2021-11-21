package com.example.yummypizza.data.database.cart

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.yummypizza.data.entities.CartItem
import com.example.yummypizza.data.entities.PizzaEntity

@Dao
interface CartDao {
    @Insert
    fun addSinglePizza(cartItem: CartItem)

    @Update
    fun updateSinglePizza(cartItem: CartItem)

    @Query("SELECT * FROM CartItem")
    fun getPizzasLiveData(): LiveData<List<CartItem>>

    @Delete
    fun deletePizza(cartItem: CartItem)
}