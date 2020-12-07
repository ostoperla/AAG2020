package com.trelp.aag2020.ui.common.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

fun Drawable.tint(context: Context, @ColorRes colorRes: Int): Drawable {
    return DrawableCompat.wrap(this).mutate().apply {
        DrawableCompat.setTint(this, ContextCompat.getColor(context, colorRes))
    }
}