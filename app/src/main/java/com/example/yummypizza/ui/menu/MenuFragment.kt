package com.example.yummypizza.ui.menu

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yummypizza.R
import com.example.yummypizza.ui.cart.CartFragment
import com.example.yummypizza.databinding.MenuFragmentBinding
import com.example.yummypizza.ui.menu.item.MenuItemBottomSheet

import com.example.yummypizza.utils.FragmentNavigator

class MenuFragment : Fragment(), View.OnClickListener {

    private var _binding: MenuFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MenuFragment()
        const val TAG = "MenuFragment"
    }

    private lateinit var viewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MenuFragmentBinding.inflate(inflater, container, false)

        binding.menuToCartButton.setOnClickListener(this)
        binding.menuItemLooker.setOnClickListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.menu_to_cart_button -> FragmentNavigator.replaceFragmentByNew(CartFragment.newInstance())
            R.id.menu_item_looker -> showMenuItemLooker()
        }
    }

    private fun showMenuItemLooker(){
        val itemLookerBottomSheet = MenuItemBottomSheet()
        itemLookerBottomSheet.show(childFragmentManager, TAG)
    }


}