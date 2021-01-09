package com.trelp.aag2020.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trelp.aag2020.data.ConfigurationCache
import com.trelp.aag2020.domain.ConfigurationRepository
import com.trelp.aag2020.domain.entity.Configuration
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class TmdbViewModel @Inject constructor(
    private val configRepository: ConfigurationRepository,
    private val configCache: ConfigurationCache
) : ViewModel() {

    private val _config = MediatorLiveData<Configuration>()
    val config: LiveData<Configuration>
        get() = _config

    init {
        loadConfiguration()
        Timber.d("init")
    }

    private fun loadConfiguration() {
        viewModelScope.launch {
            val configuration = configRepository.getConfiguration()
            configCache.put(configuration)
            _config.value = configuration
        }
    }

    override fun onCleared() {
        super.onCleared()

        Timber.d("onCleared")

        configCache.clear()
    }
}