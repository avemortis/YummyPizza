package com.example.yummypizza.data.entities

import com.google.gson.annotations.SerializedName

data class PizzaEntity (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("imageUrls") val imageUrls: List<String>,
    @SerializedName("description") val description: String
)