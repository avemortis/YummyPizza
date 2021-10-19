package com.example.yummypizza.utils.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object FragmentNavigator {
    fun setStartFragment(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment){
        fragmentManager.beginTransaction()
            .add(containerId, fragment)
            .commit()
    }

    fun Fragment.show(fragmentManager: FragmentManager, containerId: Int){
        fragmentManager.beginTransaction()
            .replace(containerId, this)
            .commit()
    }
}