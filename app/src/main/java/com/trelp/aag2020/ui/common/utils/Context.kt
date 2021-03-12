package com.trelp.aag2020.ui.common.utils

import android.content.Context
import androidx.annotation.DimenRes

fun Context.dp2pxOffset(@DimenRes dimenId: Int) = resources.getDimensionPixelOffset(dimenId)

fun Context.dp2pxSize(@DimenRes dimenId: Int) = resources.getDimensionPixelSize(dimenId)