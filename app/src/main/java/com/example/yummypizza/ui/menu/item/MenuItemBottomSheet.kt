package com.example.yummypizza.ui.menu.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.yummypizza.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.yummypizza.databinding.MenuItemBottomSheetBinding

class MenuItemBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {
    private var _binding: MenuItemBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MenuItemBottomSheetBinding.inflate(inflater, container, false)

        binding.menuItemBackToMenuItem.setOnClickListener(this)
        binding.menuItemAddToCartButton.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.menu_item_back_to_menu_item -> this.dismiss()
            R.id.menu_item_add_to_cart_button -> Toast.makeText(requireContext(), R.string.add_to_cart, Toast.LENGTH_SHORT).show()
        }
    }
}