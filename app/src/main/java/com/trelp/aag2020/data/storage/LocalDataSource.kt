package com.trelp.aag2020.data.storage

import android.content.res.AssetManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val assets: AssetManager,
    private val jsonFormat: Json
) {
    internal suspend inline fun <reified T> loadData(fileName: String): List<T> =
        withContext(Dispatchers.IO) {
            val data = readAssetFileToString(fileName)
            parseData(data)
        }

    private fun readAssetFileToString(fileName: String) =
        assets.open(fileName).bufferedReader().readText()

    private inline fun <reified T> parseData(data: String) =
        jsonFormat.decodeFromString<List<T>>(data)
}