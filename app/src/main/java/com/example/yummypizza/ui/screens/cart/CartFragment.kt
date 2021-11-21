package com.example.yummypizza.ui.screens.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yummypizza.R
import com.example.yummypizza.data.database.PizzaDatabaseRepository
import com.example.yummypizza.databinding.CartFragmentBinding
import com.example.yummypizza.ui.adapters.CartAdapter
import com.example.yummypizza.ui.adapters.OnCartItemCLickListener
import com.example.yummypizza.ui.screens.menu.MenuFragment
import com.example.yummypizza.ui.screens.order.result.OrderResultFragment
import com.example.yummypizza.utils.navigation.FragmentNavigator.show
import com.squareup.picasso.Picasso

class CartFragment : Fragment(), OnCartItemCLickListener {

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
        watchLiveData()
        return binding.root
    }

    private fun watchLiveData() {
        PizzaDatabaseRepository.getCart().observe(viewLifecycleOwner, {
            val adapter = CartAdapter(it.size, this)
            val recyclerView = binding.cartRecyclerView
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            viewModel.cart = it.toMutableList()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
    }

    private fun setOnClickListeners(){
        binding.orderConfirm
            .setOnClickListener {
                val fragment = OrderResultFragment.newInstance()
                fragment.show(
                    parentFragmentManager,
                    R.id.root_fragment
                )
            }
    }

    override fun onClick(position: Int) {
    }

    override fun onCreateViewHolder(holder: CartAdapter.CartHolder, position: Int) {
        holder.count.text = "1"
        holder.price.text = viewModel.cart[position].price.toString()
        holder.title.text = viewModel.cart[position].name
        Picasso.get().load(viewModel.cart[position].picUrl).into(holder.image)
    }
}