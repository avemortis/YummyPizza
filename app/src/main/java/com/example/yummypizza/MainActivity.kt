package com.example.yummypizza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yummypizza.ui.screens.menu.MenuFragment
import com.example.yummypizza.utils.navigation.FragmentNavigator

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
    }
}