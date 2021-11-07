package com.example.yummypizza.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity
class PizzaPicture @Inject constructor (
    @PrimaryKey(autoGenerate = true)
    var key: Int = 0,
    val pizzaKey: Int,
    val url: String
)