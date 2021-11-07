package com.example.yummypizza

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.data.database.PizzaDatabaseRepository
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.ui.screens.menu.MenuFragment
import com.example.yummypizza.utils.navigation.FragmentNavigator
import com.example.yummypizza.utils.navigation.FragmentNavigator.show
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeWindowBars(true)

        if (savedInstanceState == null){
            FragmentNavigator.setStartFragment(
                supportFragmentManager,
                R.id.root_fragment,
                MenuFragment.newInstance()
            )
        }
        getMenuAndAddToDatabase(appComponent.getPizzaService())
    }

    override fun onDestroy() {
        super.onDestroy()
        PizzaDatabaseRepository.clearPizzaDatabase()
        PizzaDatabaseRepository.clearPictureDatabase()
    }

    fun getMenuAndAddToDatabase(service: PizzaService) {
        service.getAllPizzas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<PizzaEntity>> {
                override fun onSuccess(t: List<PizzaEntity>) {
                    PizzaDatabaseRepository.addAllPizzas(t)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    getMenuAndAddToDatabase(service)
                }
            })
    }

    fun changeWindowBars(toLight : Boolean){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (toLight){
                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)
            } else {
                window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)
            }

        } else {
            @Suppress("DEPRECATION")
            if (toLight) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.background_gray)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.background_gray)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            else {
                window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
                window.decorView.systemUiVisibility = 0
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) super.onBackPressed()
        else supportFragmentManager.popBackStack()
    }
}