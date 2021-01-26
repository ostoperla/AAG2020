package com.trelp.aag2020.ui.movies

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.trelp.aag2020.R
import com.trelp.aag2020.data.Movie
import com.trelp.aag2020.data.isSame
import com.trelp.aag2020.data.loadMovies
import com.trelp.aag2020.databinding.FragmentMoviesListBinding
import com.trelp.aag2020.ui.common.BaseFragment
import com.trelp.aag2020.ui.common.utils.dp2pxOffset
import kotlinx.coroutines.*

class FragmentMoviesList : BaseFragment(R.layout.fragment_movies_list) {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val binding
        get() = viewBinding!! as FragmentMoviesListBinding

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }

    private var itemClickListener: OnItemClickListener? = null

    private val movieAdapter by lazy {
        MovieAdapter(
            { old, new -> old.isSame(new) },
            itemClickListener
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        itemClickListener =
            if (context is OnItemClickListener) context
            else throw ClassCastException("${context.javaClass} must implement OnItemClickListener")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMoviesListBinding.bind(view)

        initMoviesList()
    }

    override fun onStart() {
        super.onStart()

        scope.launch {
            context?.applicationContext?.let { context ->
                val movies = loadMovies(context)
                movieAdapter.submitList(movies)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun initMoviesList() {
        with(binding.listMovie) {
            setHasFixedSize(true)
            adapter = movieAdapter
            addItemDecoration(
                MovieOffsetItemDecoration(context.dp2pxOffset(R.dimen.item_movie_offset))
            )
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
    }

    override fun onDetach() {
        itemClickListener = null

        super.onDetach()
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}