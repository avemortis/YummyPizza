package com.example.yummypizza.data.database

import android.content.Context
import androidx.room.Room
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.data.entities.PizzaPicture
import java.util.concurrent.Executors

private const val DATABASE_NAME = "yummy_pizza_database"

object PizzaDatabaseRepository {
    private lateinit var database: PizzaDatabase
    private lateinit var pizzaDAO: PizzaDAO
    private val executor = Executors.newSingleThreadExecutor()
    //Pizza
    fun addPizza(pizzaEntity: PizzaEntity) {
        executor.execute {
            pizzaDAO.addSinglePizza(pizzaEntity)
        }
    }

    fun addAllPizzas(list : List<PizzaEntity>) {
        executor.execute {
            pizzaDAO.addPizzas(list)

            list.forEach { entity ->
                entity.imageUrls.forEach { url ->
                    val pic = PizzaPicture(0, entity.id - 1, url)
                    addPic(pic)
                }
            }
        }
    }

    fun getPizzas() = pizzaDAO.getPizzas()

    fun getSinglePizza(id: Int) = pizzaDAO.getSinglePizza(id)

    fun getPizzasLiveData() = pizzaDAO.getPizzasLiveData().apply {
        value?.forEach{ pizza ->
            pizzaDAO.getPicsForPizza(pizza.key).forEach {
                pizza.imageUrls.add(it.url)
            }
        }
    }

    fun getSinglePizzaLiveData(id: Int) = pizzaDAO.getSinglePizzaLiveData(id)

    fun deletePizza(pizzaEntity: PizzaEntity) {
        executor.execute {
            pizzaDAO.deletePizza(pizzaEntity)
        }
    }

    fun clearPizzaDatabase() {
        executor.execute {
            pizzaDAO.clearPizzaDatabase()
        }
    }

    //Picture
    fun addPic(picture: PizzaPicture){
        executor.execute {
            pizzaDAO.addPic(picture)
        }
    }

    fun addAllPics(list: List<PizzaPicture>){
        executor.execute {
            pizzaDAO.addAllPics(list)
        }
    }

    fun getPicsForPizza(pizzaId: Int) = pizzaDAO.getPicsForPizza(pizzaId)

    fun getAllPics() = pizzaDAO.getAllPics()

    fun clearPictureDatabase() {
        executor.execute {
            pizzaDAO.clearPicDatabase()
        }
    }

    fun init(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            PizzaDatabase::class.java,
            DATABASE_NAME
        ).build()

        pizzaDAO = database.DAO()
    }
}