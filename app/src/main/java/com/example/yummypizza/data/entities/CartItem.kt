package com.example.yummypizza.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity
class CartItem @Inject constructor() {
    @PrimaryKey
    var id = 0
    var price = 0
    var picUrl = String()
    var name = String()
}