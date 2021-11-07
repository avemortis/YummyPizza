package com.example.yummypizza.ui.screens.menu

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.data.database.PizzaDatabaseRepository
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.utils.injections.entity.PizzaConnector
import io.reactivex.FlowableSubscriber
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription
import javax.inject.Inject

class MenuViewModel @Inject constructor() : ViewModel() {
    private lateinit var menuFull: List<PizzaEntity>

    var prevMenu: List<PizzaEntity> = listOf()

    val menuObservable: BehaviorProcessor<List<PizzaEntity>> by lazy {
        val processor = BehaviorProcessor.create<List<PizzaEntity>>()
        processor.offer(listOf())
        processor
    }

    fun getMenu(lifecycleOwner: LifecycleOwner) {
        PizzaDatabaseRepository.getPizzasLiveData().observe(lifecycleOwner, { pizzaList ->
            menuFull = pizzaList
            menuObservable.offer(pizzaList)
        })
    }

    fun getMenu() {
        PizzaDatabaseRepository.getPizzas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<PizzaEntity>> {
                override fun onSuccess(t: List<PizzaEntity>) {
                    menuFull = t
                    menuObservable.offer(t)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    fun getMenu(service: PizzaService) {
        service.getAllPizzas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<PizzaEntity>> {
                override fun onSuccess(t: List<PizzaEntity>) {
                    menuFull = t
                    menuObservable.offer(t)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    getMenu(service)
                }
            })
    }

    fun searchByMenu(string: String?) {
        val menuToShow: MutableList<PizzaEntity> = mutableListOf()
        if (string != null) {
            menuFull.forEach {
                if ((it.name.indexOf(string, 0) != -1)
                    || (it.description.indexOf(string, 0) != -1)
                ) {
                    menuToShow.add(it)
                }
            }
        }
        prevMenu = menuObservable.value!!
        menuObservable.offer(menuToShow)
    }
}