package com.example.yummypizza.ui.screens.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.data.entities.PizzaEntity
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MenuViewModel : ViewModel() {
    private lateinit var menuFull : List<PizzaEntity>
    val compositeDisposable = CompositeDisposable()
    val menuLiveData : MutableLiveData<List<PizzaEntity>> = MutableLiveData()

    fun getMenu(service: PizzaService){
        service.getAllPizzas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<PizzaEntity>> {
                override fun onSuccess(t: List<PizzaEntity>) {
                    menuFull = t
                    menuLiveData.value = menuFull
                }
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }
                override fun onError(e: Throwable) {

                }
            })
    }

    fun searchByMenu(string: String?){
        val menuToShow : MutableList<PizzaEntity> = mutableListOf()
        if (string!=null){
            menuFull.forEach{
                if((it.name.indexOf(string, 0) != -1)
                    ||(it.description.indexOf(string, 0) != -1)){
                    menuToShow.add(it)
                }
            }
        }
        menuLiveData.value = menuToShow
    }
}