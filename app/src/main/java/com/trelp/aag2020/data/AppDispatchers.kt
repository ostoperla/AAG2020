package com.trelp.aag2020.data

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AppDispatchers @Inject constructor() : DispatchersProvider {
    override val default = Dispatchers.Default
    override val io = Dispatchers.IO
    override val main = Dispatchers.Main
    override val unconfined = Dispatchers.Unconfined
}