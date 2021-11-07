package com.example.yummypizza.utils.injections.entity

import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.data.entities.PizzaPicture

object PizzaConnector {
    fun connect(pizzas: List<PizzaEntity>, pictures: List<PizzaPicture>){
        pizzas.forEach { entity ->
            var pos = 0
            for (i in pos..pictures.size){
                if (pictures[i].pizzaKey == entity.id){
                    entity.imageUrls.add(pictures[i].url)
                }
                else {
                    pos = i
                    break
                }
            }
        }
    }
}