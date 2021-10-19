package com.example.yummypizza.ui.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yummypizza.R
import com.example.yummypizza.databinding.CartFragmentBinding
import com.example.yummypizza.ui.menu.MenuFragment
import com.example.yummypizza.ui.order.result.OrderResultFragment
import com.example.yummypizza.utils.FragmentNavigator
import com.example.yummypizza.utils.FragmentNavigator.show

class CartFragment : Fragment() {

    private var _binding: CartFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CartFragmentBinding.inflate(inflater, container, false)

        setOnClickListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
    }

    private fun setOnClickListeners(){
        binding.cartConfirmOrderButton
            .setOnClickListener {
                val fragment = OrderResultFragment.newInstance()
                fragment.show(
                    parentFragmentManager,
                    R.id.root_fragment
                )
            }
        binding.cartBackToMenuButton.
        setOnClickListener {
            val fragment = MenuFragment.newInstance()
            fragment.show(
                parentFragmentManager,
                R.id.root_fragment
            )
        }

    }
}