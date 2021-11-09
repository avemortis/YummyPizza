package com.example.yummypizza.data.database

import android.content.Context
import androidx.room.Room
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.data.entities.PizzaPicture
import java.util.concurrent.Executors

object PizzaDatabaseRepository {
    const val DATABASE_NAME = "yummy_pizza_database"
    private lateinit var database: PizzaDatabase
    private lateinit var pizzaDAO: PizzaDAO
    private val executor = Executors.newSingleThreadExecutor()
    //Pizza
    fun addPizza(pizzaEntity: PizzaEntity) {
        executor.execute {
            pizzaDAO.addSinglePizza(pizzaEntity)
        }
    }

    fun addAllPizzas(list : List<PizzaEntity>, defaultPicture: PizzaPicture) {
        executor.execute {
            list.forEach { entity ->
                entity.imageUrls.forEach{
                    defaultPicture.pizzaKey = entity.id
                    defaultPicture.url = it
                    pizzaDAO.addPic(defaultPicture)
                }
                entity.firstImageUrl = entity.imageUrls[0]
                pizzaDAO.addSinglePizza(entity)
            }
        }
    }

    fun getPizzas() = pizzaDAO.getPizzas()

    fun getSinglePizza(id: Int) = pizzaDAO.getSinglePizza(id)

    fun getPizzasLiveData() = pizzaDAO.getPizzasLiveData()

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