package com.trelp.aag2020.domain

import com.trelp.aag2020.domain.entity.Configuration

interface ConfigurationRepository {
    suspend fun getConfiguration(): Configuration
}
