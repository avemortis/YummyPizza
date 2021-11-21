package com.example.yummypizza.data.database.menu

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.data.entities.PizzaPicture
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface PizzaDAO {
    @Insert
    fun addSinglePizza(pizzaEntity: PizzaEntity)

    @Update
    fun updateSinglePizza(pizzaEntity: PizzaEntity)

    @Insert
    fun addPizzas(list : List<PizzaEntity>)

    @Query("SELECT * FROM PizzaEntity")
    fun getPizzas(): Single<List<PizzaEntity>>

    @Query("SELECT * FROM PizzaEntity WHERE id LIKE:id")
    fun getSinglePizza(id: Int): Single<PizzaEntity>

    @Query("SELECT * FROM PizzaEntity")
    fun getPizzasLiveData(): LiveData<List<PizzaEntity>>

    @Query("SELECT * FROM PizzaEntity WHERE id LIKE:id")
    fun getSinglePizzaLiveData(id: Int): LiveData<PizzaEntity>

    @Delete
    fun deletePizza(pizzaEntity: PizzaEntity)

    @Query("DELETE FROM PizzaEntity")
    fun clearPizzaDatabase()

    @Insert
    fun addPic(pic: PizzaPicture)

    @Insert
    fun addAllPics(list : List<PizzaPicture>)

    @Query("SELECT * FROM PizzaPicture")
    fun getAllPics() : LiveData<List<PizzaPicture>>

    @Query("SELECT * FROM PizzaPicture WHERE pizzaKey LIKE:id")
    fun getPicsForPizza(id: Int) : Single<List<PizzaPicture>>

    @Delete
    fun deletePic(pic: PizzaPicture)

    @Query("DELETE FROM PizzaPicture")
    fun clearPicDatabase()
}