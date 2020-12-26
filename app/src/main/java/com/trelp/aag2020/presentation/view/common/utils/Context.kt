package com.trelp.aag2020.presentation.view.common.utils

import android.content.Context
import android.graphics.drawable.Drawable

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

fun Context.color(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.tintDrawable(@DrawableRes drawableRes: Int, @ColorRes colorRes: Int): Drawable {
    val source = requireNotNull(ContextCompat.getDrawable(this, drawableRes))
    val wrapped = DrawableCompat.wrap(source).mutate()
    DrawableCompat.setTint(wrapped, color(colorRes))
    return wrapped
}

fun Context.dp2pxOffset(@DimenRes dimenId: Int) = resources.getDimensionPixelOffset(dimenId)

fun Context.dp2pxSize(@DimenRes dimenId: Int) = resources.getDimensionPixelSize(dimenId)