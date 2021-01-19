package com.trelp.aag2020.domain.interactor

import com.trelp.aag2020.domain.repository.ActorRepository
import com.trelp.aag2020.domain.entity.Actor
import javax.inject.Inject

class ActorInteractor @Inject constructor(
    private val actorRepository: ActorRepository
) {
    suspend fun getActors(movieId: Int): List<Actor> {
        return actorRepository.getActors(movieId)
    }
}