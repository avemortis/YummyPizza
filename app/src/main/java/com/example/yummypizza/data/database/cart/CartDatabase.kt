package com.example.yummypizza.data.database.cart

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yummypizza.data.entities.CartItem
import com.example.yummypizza.data.entities.PizzaEntity

@Database(entities = [CartItem::class], version = 1, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {
    abstract fun DAO() : CartDao
}