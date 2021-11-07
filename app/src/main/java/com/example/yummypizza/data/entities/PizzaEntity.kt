package com.example.yummypizza.data.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

@Entity
data class PizzaEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("description") val description: String,
    @Ignore
    @SerializedName("imageUrls") val imageUrls: MutableList<String>,
    var firstImageUrl: String = String(),
    @PrimaryKey(autoGenerate = true) var key: Int = 0
) {
    constructor(id: Int, name: String, price: Int, description: String) : this(id, name, price, description, mutableListOf())
}