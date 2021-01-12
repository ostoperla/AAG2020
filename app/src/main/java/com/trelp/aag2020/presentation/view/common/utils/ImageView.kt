package com.trelp.aag2020.presentation.view.common.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.trelp.aag2020.R

fun ImageView.loadImage(imageUrl: String?) {

    val requestOptions = RequestOptions().apply {
        placeholder(R.color.scarpa_flow)
        error(R.color.radical_red)
    }

    Glide.with(this)
        .load(imageUrl)
        .apply(requestOptions)
        .into(this)
}
