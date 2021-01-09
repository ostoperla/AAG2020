package com.trelp.aag2020.data

import com.trelp.aag2020.domain.entity.Configuration
import com.trelp.aag2020.domain.entity.ConfigurationCacheItem
import timber.log.Timber
import javax.inject.Inject

class ConfigurationCache @Inject constructor(
    private val lifeTime: Long
) {
    private var cache: ConfigurationCacheItem? = null

    fun put(configuration: Configuration) {
        cache = ConfigurationCacheItem(System.currentTimeMillis(), configuration)
        Timber.d("put")
    }

    fun get(): Configuration? {
        Timber.d("get")
        return cache?.let {
            if (System.currentTimeMillis() - it.time > lifeTime)
                null
            else
                it.data
        }

    }

    fun clear() {
        cache = null
        Timber.d("clear")
    }
}