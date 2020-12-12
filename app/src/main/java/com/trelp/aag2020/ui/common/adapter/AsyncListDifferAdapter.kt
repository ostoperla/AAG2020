package com.trelp.aag2020.ui.common.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class AsyncListDifferAdapter<T, VH : RecyclerView.ViewHolder>(
    private val itemDiff: (old: T, new: T) -> Boolean
) : ListAdapter<T, VH>(
    object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) =
            if (oldItem === newItem) true else itemDiff.invoke(oldItem, newItem)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
    }
)