package com.example.yummypizza.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.databinding.MenuItemBinding
import com.squareup.picasso.Picasso

class MenuAdapter(
    var size: Int,
    private val listener: OnMenuItemCLickListener
) : RecyclerView.Adapter<MenuAdapter.MenuHolder>() {

    class MenuHolder(binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.menuItemImage
        val title = binding.menuItemTitle
        val info = binding.menuItemInfo
        val price = binding.menuItemPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MenuHolder(binding)

        binding.root.setOnClickListener {
            listener.onClick(holder.bindingAdapterPosition)
        }

        return holder
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        listener.onCreateViewHolder(holder, position)
    }

    override fun getItemCount() = size


}

interface OnMenuItemCLickListener {
    fun onClick(position: Int)
    fun onCreateViewHolder(holder: MenuAdapter.MenuHolder, position: Int)
}