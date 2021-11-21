package com.example.yummypizza.data.database

import android.content.Context
import androidx.room.Room
import com.example.yummypizza.data.database.cart.CartDao
import com.example.yummypizza.data.database.cart.CartDatabase
import com.example.yummypizza.data.database.menu.PizzaDAO
import com.example.yummypizza.data.database.menu.PizzaDatabase
import com.example.yummypizza.data.entities.CartItem
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.data.entities.PizzaPicture
import java.util.concurrent.Executors

object PizzaDatabaseRepository {
    const val DATABASE_NAME = "yummy_pizza_database"
    private lateinit var database: PizzaDatabase
    private lateinit var pizzaDAO: PizzaDAO

    const val CART_DATABASE_NAME = "cart_database"
    private lateinit var cart: CartDatabase
    private lateinit var cartDao: CartDao
    private val executor = Executors.newSingleThreadExecutor()
    //Pizza

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

    fun updatePizza(pizzaEntity: PizzaEntity){
        executor.execute {
            pizzaDAO.updateSinglePizza(pizzaEntity)
        }
    }

    fun getPizzas() = pizzaDAO.getPizzas()

    fun getSinglePizza(id: Int) = pizzaDAO.getSinglePizza(id)

    fun getPizzasLiveData() = pizzaDAO.getPizzasLiveData()

    fun getPicsForPizza(pizzaId: Int) = pizzaDAO.getPicsForPizza(pizzaId)

    fun addToCart(cartItem: CartItem){
        executor.execute {
            cartDao.addSinglePizza(cartItem)
        }
    }

    fun getCart() = cartDao.getPizzasLiveData()

    fun init(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            PizzaDatabase::class.java,
            DATABASE_NAME
        ).build()

        pizzaDAO = database.DAO()

        cart = Room.databaseBuilder(
            context.applicationContext,
            CartDatabase::class.java,
            CART_DATABASE_NAME
        ).build()

        cartDao = cart.DAO()
    }
}