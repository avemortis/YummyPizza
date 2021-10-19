package com.example.yummypizza.ui.order.result

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yummypizza.R
import com.example.yummypizza.databinding.OrderResultFragmentBinding
import com.example.yummypizza.ui.menu.MenuFragment
import com.example.yummypizza.utils.FragmentNavigator.show

class OrderResultFragment : Fragment() {

    private var _binding: OrderResultFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = OrderResultFragment()
    }

    private lateinit var viewModel: OrderResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OrderResultFragmentBinding.inflate(inflater, container, false)

        setOnClickListeners()

        return binding.root
    }

    private fun setOnClickListeners(){
        binding.orderResultBackToMenuButton.setOnClickListener {
            val fragment = MenuFragment.newInstance()
            fragment.show(
                parentFragmentManager,
                R.id.root_fragment
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderResultViewModel::class.java)
    }

}