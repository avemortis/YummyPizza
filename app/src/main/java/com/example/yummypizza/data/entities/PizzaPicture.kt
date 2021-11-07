package com.example.yummypizza.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity
class PizzaPicture(
    var pizzaKey: Int = 0,
    var url: String = String(),
    @PrimaryKey(autoGenerate = true)
    var key: Int = 0
)

