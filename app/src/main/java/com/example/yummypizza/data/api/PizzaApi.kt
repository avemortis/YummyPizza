package com.example.yummypizza.data.api

import com.example.yummypizza.data.entities.PizzaEntity
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PizzaApi {
    @GET("pizza")
    fun getAllPizzas() : Single<List<PizzaEntity>>

    @GET("pizza/{id}")
    fun getPizzaById(
        @Path("id", encoded = false) id : Int
    ) : Single<PizzaEntity>
}