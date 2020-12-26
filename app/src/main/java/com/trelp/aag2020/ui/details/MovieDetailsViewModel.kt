package com.trelp.aag2020.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trelp.aag2020.data.Actor
import com.trelp.aag2020.data.Movie

class MovieDetailsViewModel(
    private val movie: Movie
) : ViewModel() {

    private val _actors = MutableLiveData<List<Actor>>()
    val actors: LiveData<List<Actor>>
        get() = _actors

    init {
        loadActors()
    }

    private fun loadActors() {
        _actors.value = movie.actors
    }

    companion object {
        fun factory(movie: Movie) = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>) =
                if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
                    MovieDetailsViewModel(movie) as T
                } else {
                    throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
                }
        }
    }
}