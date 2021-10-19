package com.example.yummypizza.ui.menu.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.yummypizza.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.yummypizza.databinding.MenuItemBottomSheetBinding

class MenuItemBottomSheet : BottomSheetDialogFragment() {
    private var _binding: MenuItemBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MenuItemBottomSheetBinding.inflate(inflater, container, false)

        setOnClickListeners()

        return binding.root
    }

    private fun setOnClickListeners(){
        binding.menuItemBackToMenuItem.setOnClickListener {
            this.dismiss()
        }

        binding.menuItemAddToCartButton.setOnClickListener {
            Toast.makeText(requireContext(), R.string.add_to_cart, Toast.LENGTH_SHORT).show()
        }

    }

}