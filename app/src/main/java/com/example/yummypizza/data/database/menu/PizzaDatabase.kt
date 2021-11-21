package com.example.yummypizza.data.database.menu

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.data.entities.PizzaPicture

@Database(entities = [PizzaEntity::class, PizzaPicture::class], version = 1, exportSchema = false)
abstract class PizzaDatabase : RoomDatabase() {
    abstract fun DAO(): PizzaDAO
}