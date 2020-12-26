package com.trelp.aag2020.ui.movies

import android.content.Context
import androidx.lifecycle.*
import com.trelp.aag2020.data.Movie
import com.trelp.aag2020.data.loadMovies
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val appContext: Context,
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    init {
        loadMoviesList()
    }

    private fun loadMoviesList() {
        viewModelScope.launch {
            _movies.value = loadMovies(appContext)
        }
    }

    companion object {
        fun factory(appContext: Context) = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>) =
                if (modelClass.isAssignableFrom(MoviesListViewModel::class.java)) {
                    MoviesListViewModel(appContext) as T
                } else {
                    throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
                }
        }
    }
}