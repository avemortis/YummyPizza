package com.example.yummypizza.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object FragmentNavigator {
    var fragmentManager : FragmentManager? = null
    var containerId: Int? = null

    fun setStartFragment(fragment: Fragment){
        fragmentManager!!.beginTransaction()
            .add(containerId!!, fragment)
            .commit()
    }

    fun replaceFragmentByNew(fragment: Fragment){
        fragmentManager!!.beginTransaction()
            .replace(containerId!!, fragment)
            .commit()
    }
}