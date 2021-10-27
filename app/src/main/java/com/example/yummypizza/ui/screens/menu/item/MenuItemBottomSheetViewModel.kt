package com.example.yummypizza.ui.screens.menu.item

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.data.entities.PizzaEntity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MenuItemBottomSheetViewModel : ViewModel() {
    lateinit var bundle: Bundle
    private val index get() = bundle.getInt(MenuItemBottomSheet.TAG)
    lateinit var pizzaEntity: PizzaEntity

    val compositeDisposable = CompositeDisposable()
    val loadStatus: PublishSubject<Boolean> = PublishSubject.create()

    fun getPizza(service: PizzaService) {
        service.getPizzaById(index)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<PizzaEntity> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: PizzaEntity) {
                    pizzaEntity = t
                    loadStatus.onNext(true)
                }

                override fun onError(e: Throwable) {
                }
            })
    }
}