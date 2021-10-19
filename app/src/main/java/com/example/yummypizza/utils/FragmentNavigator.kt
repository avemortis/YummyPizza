package com.example.yummypizza.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.yummypizza.ui.menu.MenuFragment
import com.example.yummypizza.ui.order.result.OrderResultFragment

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