package com.example.yummypizza.ui.screens.menu.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.yummypizza.databinding.MenuItemBottomSheetBinding
import com.squareup.picasso.Picasso

class MenuItemBottomSheet : BottomSheetDialogFragment() {
    private var _binding: MenuItemBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MenuItemBottomSheetViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MenuItemBottomSheetBinding
            .inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuItemBottomSheetViewModel::class.java)
        viewModel.bundle = requireArguments()

        startLiveDataWatch()
    }

    private fun setOnClickListeners(){
    }

    private fun startLiveDataWatch(){
        viewModel.getPizzaLiveData().observe(this, {
            binding.menuBottomSheetTitle.text = it.name
            binding.menuBottomSheetDescription.text = it.description
            binding.menuBottomSheetAddtocart.text = "${binding.menuBottomSheetAddtocart.text} ${it.price}"
            Picasso.get().load(it.imageUrl).into(binding.menuBottomSheetImage)
        })
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