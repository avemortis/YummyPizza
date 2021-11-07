package com.example.yummypizza

import android.app.Application
import android.content.Context
import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.data.database.PizzaDatabaseRepository
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.utils.injections.AppComponent
import com.example.yummypizza.utils.injections.DaggerAppComponent
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class YummyPizzaApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        PizzaDatabaseRepository.init(this)
        appComponent = DaggerAppComponent.create()
    }

    fun getMenuAndAddToDatabase(service: PizzaService) {
        service.getAllPizzas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<PizzaEntity>> {
                override fun onSuccess(t: List<PizzaEntity>) {
                    t.forEach {
                        it.firstImageUrl = it.imageUrls[0]
                    }
                    PizzaDatabaseRepository.addAllPizzas(t)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    getMenuAndAddToDatabase(service)
                }
            })
    }
}



val Context.appComponent: AppComponent
    get() = when (this) {
        is YummyPizzaApplication -> appComponent
        else -> this.applicationContext.appComponent
    }