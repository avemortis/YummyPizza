package com.example.yummypizza.utils.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.yummypizza.R

object FragmentNavigator {
    val root = R.id.root_fragment

    fun setStartFragment(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment){
        fragmentManager.beginTransaction()
            .add(containerId, fragment)
            .commit()
    }

    fun Fragment.show(fragmentManager: FragmentManager, containerId: Int){
        fragmentManager.beginTransaction()
            .replace(containerId, this)
            .addToBackStack(fragmentManager.backStackEntryCount.toString())
            .commit()
    }

    fun AppCompatActivity.backPressed(){
        this.onBackPressed()
    }
}