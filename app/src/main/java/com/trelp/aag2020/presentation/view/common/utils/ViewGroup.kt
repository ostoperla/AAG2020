package com.trelp.aag2020.presentation.view.common.utils

import android.view.LayoutInflater
import android.view.ViewGroup

inline val ViewGroup.inflater: LayoutInflater
    get() = LayoutInflater.from(this.context)