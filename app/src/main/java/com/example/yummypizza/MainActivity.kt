package com.example.yummypizza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.yummypizza.ui.menu.MenuFragment
import com.example.yummypizza.utils.FragmentNavigator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FragmentNavigator.apply {
            fragmentManager = supportFragmentManager
            if (savedInstanceState == null){
                containerId = R.id.root_fragment
                setStartFragment(MenuFragment.newInstance())
            }
        }

        setContentView(R.layout.activity_main)
    }
}