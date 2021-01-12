package com.trelp.aag2020.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.trelp.aag2020.data.model.ConfigurationResponse
import com.trelp.aag2020.di.AppContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PrefsManager @Inject constructor(
    @AppContext private val context: Context,
    private val json: Json
) {
    private val tmdbConfigPrefs by lazy { getSharedPreferences(TMDB_CONFIG_PREFS) }

    private fun getSharedPreferences(fileName: String): SharedPreferences {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    var config: ConfigurationResponse?
        get() = tmdbConfigPrefs.getString(KEY_CONFIG, null)?.let {
            json.decodeFromString(it)
        }
        set(value) = tmdbConfigPrefs.edit { putString(KEY_CONFIG, json.encodeToString(value)) }

    companion object {
        private const val TMDB_CONFIG_PREFS = "config_prefs"
        private const val KEY_CONFIG = "config"
    }
}