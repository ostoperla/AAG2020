package com.trelp.aag2020.data

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AppDispatchers @Inject constructor() : DispatchersProvider {

    override fun default() = Dispatchers.Default

    override fun io() = Dispatchers.IO

    override fun main() = Dispatchers.Main

    override fun unconfined() = Dispatchers.Unconfined
}