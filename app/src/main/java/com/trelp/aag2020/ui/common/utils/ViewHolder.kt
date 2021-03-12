package com.trelp.aag2020.ui.common.utils

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

inline val RecyclerView.ViewHolder.context: Context
    get() = itemView.context