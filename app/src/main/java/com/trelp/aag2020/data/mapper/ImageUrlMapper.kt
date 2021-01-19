package com.trelp.aag2020.data.mapper

import javax.inject.Inject

class ImageUrlMapper @Inject constructor() {

    fun map(imageSize: String, relativeUrl: String?): String? {
        return relativeUrl?.let {
            "$SECURE_BASE_URL$imageSize$it"
        }
    }

    companion object {
        private const val SECURE_BASE_URL = "https://image.tmdb.org/t/p/"
    }
}
