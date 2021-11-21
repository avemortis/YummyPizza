package com.example.yummypizza.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yummypizza.databinding.CartItemBinding

class CartAdapter(
    var size: Int,
    private val listener: OnCartItemCLickListener
) : RecyclerView.Adapter<CartAdapter.CartHolder>() {

    class CartHolder(binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.cartItemImage
        val title = binding.cartItemName
        val price = binding.cartItemPrice
        val count = binding.cartItemCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = CartHolder(binding)

        binding.root.setOnClickListener {
            listener.onClick(holder.bindingAdapterPosition)
        }

        return holder
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        if (position != -1){
            listener.onCreateViewHolder(holder, position)
        }
    }

    override fun getItemCount() = size
}

interface OnCartItemCLickListener {
    fun onClick(position: Int)
    fun onCreateViewHolder(holder: CartAdapter.CartHolder, position: Int)
}