package com.trelp.aag2020.domain.repository

import com.trelp.aag2020.domain.entity.Actor

interface ActorRepository {
    suspend fun getActors(movieId: Int): List<Actor>
}