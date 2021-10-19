package com.example.yummypizza.ui.screens.menu.item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.battisq.pizzamarket.PizzaDatabase
import com.battisq.pizzamarket.PizzaEntity
import com.example.yummypizza.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.yummypizza.databinding.MenuItemBottomSheetBinding
import com.squareup.picasso.Picasso

class MenuItemBottomSheet : BottomSheetDialogFragment() {
    private var _binding: MenuItemBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val index = arguments?.getInt(TAG)
        val pizza = PizzaDatabase.pizzaDao.getAll()[index!!]
        _binding = MenuItemBottomSheetBinding.inflate(inflater, container, false)

        binding.menuBottomSheetTitle.text = pizza.name
        binding.menuBottomSheetDescription.text = pizza.description
        binding.menuBottomSheetAddtocart.text = "${binding.menuBottomSheetAddtocart.text} ${pizza.price}"
        Picasso.get().load(pizza.imageUrl).into(binding.menuBottomSheetImage)

        return binding.root
    }

    private fun setOnClickListeners(){
    }

    companion object {
        const val TAG = "MenuLooker"
        fun newInstance(pizzaIndex : Int) = MenuItemBottomSheet().apply{
            arguments = Bundle().apply {
                putInt(TAG, pizzaIndex)
            }
        }
    }

}