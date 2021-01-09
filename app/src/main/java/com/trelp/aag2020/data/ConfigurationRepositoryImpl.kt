package com.trelp.aag2020.data

import com.trelp.aag2020.data.network.TmdbAPI
import com.trelp.aag2020.domain.ConfigurationRepository
import com.trelp.aag2020.domain.entity.Configuration
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val api: TmdbAPI,
    private val dispatchers: DispatchersProvider,
) : ConfigurationRepository {

    override suspend fun getConfiguration(): Configuration = withContext(dispatchers.io()) {
        val configuration = api.getConfiguration()

        Configuration(
            configuration.images.secureBaseUrl
        )
    }
}
