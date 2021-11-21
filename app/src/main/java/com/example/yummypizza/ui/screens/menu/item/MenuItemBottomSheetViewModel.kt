package com.example.yummypizza.ui.screens.menu.item

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.data.database.PizzaDatabaseRepository
import com.example.yummypizza.data.entities.PizzaEntity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MenuItemBottomSheetViewModel @Inject constructor () : ViewModel() {
    lateinit var bundle: Bundle
    val index get() = bundle.getInt(MenuItemBottomSheet.TAG)

    lateinit var pizza : PizzaEntity

    fun getSinglePizza() = PizzaDatabaseRepository.getSinglePizza(index)
}