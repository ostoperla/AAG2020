package com.trelp.aag2020.data

import kotlinx.coroutines.Dispatchers

class AppDispatchers : DispatchersProvider {

    override fun default() = Dispatchers.Default

    override fun io() = Dispatchers.IO

    override fun main() = Dispatchers.Main

    override fun unconfined() = Dispatchers.Unconfined
}