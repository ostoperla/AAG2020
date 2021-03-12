package com.trelp.aag2020.data.repository

import com.trelp.aag2020.data.DispatchersProvider
import com.trelp.aag2020.data.mapper.ActorMapper
import com.trelp.aag2020.data.network.TmdbAPI
import com.trelp.aag2020.domain.repository.ActorRepository
import com.trelp.aag2020.domain.entity.Actor
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActorRepositoryImpl @Inject constructor(
    private val api: TmdbAPI,
    private val dispatchers: DispatchersProvider,
    private val actorMapper: ActorMapper
) : ActorRepository {

    override suspend fun getActors(movieId: Int): List<Actor> = withContext(dispatchers.io) {
        api.getMovieCredits(movieId).cast.map {
            actorMapper.map(it)
        }
    }
}