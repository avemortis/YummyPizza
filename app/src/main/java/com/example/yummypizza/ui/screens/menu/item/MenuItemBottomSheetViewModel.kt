package com.example.yummypizza.ui.screens.menu.item

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.data.entities.PizzaEntity
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MenuItemBottomSheetViewModel : ViewModel() {
    lateinit var bundle: Bundle
    //private val index get() = bundle.getInt(MenuItemBottomSheet.TAG)
    private val pizzaLiveData: MutableLiveData<PizzaEntity> = MutableLiveData()

    fun getPizzaLiveData(service : PizzaService) : LiveData<PizzaEntity>{
        var index = 0
        try {
            index = bundle.getInt(MenuItemBottomSheet.TAG)
        } catch (e : Exception){
            throw (IllegalStateException("No bundle or bundle das not contain index"))
        }

            service.getPizzaById(index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<PizzaEntity> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onSuccess(t: PizzaEntity) {
                        pizzaLiveData.value = t
                    }

                    override fun onError(e: Throwable) {
                    }

                })

        return pizzaLiveData
    }
}