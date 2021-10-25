package com.example.yummypizza.data.api

import android.util.Log
import com.example.yummypizza.data.entities.PizzaEntity
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class PizzaService {
    private val pizzaApi : PizzaApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://springboot-kotlin-demo.herokuapp.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        pizzaApi = retrofit.create(PizzaApi::class.java)
    }

    fun getAllPizzas() : Single<List<PizzaEntity>> = pizzaApi.getAllPizzas()

    fun getPizzaById(id : Int) : Single<PizzaEntity> {
        return pizzaApi.getPizzaById(id)
    }
}