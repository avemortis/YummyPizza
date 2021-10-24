package com.example.yummypizza.utils.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.yummypizza.data.entities.PizzaEntity


class MenuDiffUtil(val oldList : List<PizzaEntity>, val newList : List <PizzaEntity>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}