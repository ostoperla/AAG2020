package com.trelp.aag2020.data

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    fun default(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}