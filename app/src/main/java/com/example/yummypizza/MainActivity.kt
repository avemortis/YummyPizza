package com.example.yummypizza

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import com.example.yummypizza.ui.screens.menu.MenuFragment
import com.example.yummypizza.utils.navigation.FragmentNavigator
import com.example.yummypizza.utils.navigation.FragmentNavigator.show

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null){
            FragmentNavigator.setStartFragment(
                supportFragmentManager,
                R.id.root_fragment,
                MenuFragment.newInstance()
            )
        }

        setContentView(R.layout.activity_main)
        changeWindowBars(true)
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