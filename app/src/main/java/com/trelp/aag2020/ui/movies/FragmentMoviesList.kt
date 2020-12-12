package com.trelp.aag2020.ui.movies

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.GridLayoutManager
import com.trelp.aag2020.R
import com.trelp.aag2020.data.MoviesDataSource
import com.trelp.aag2020.data.isSame
import com.trelp.aag2020.databinding.FragmentMoviesListBinding
import com.trelp.aag2020.ui.common.BaseFragment
import com.trelp.aag2020.ui.common.utils.dp2pxOffset
import com.trelp.aag2020.ui.details.FragmentMovieDetails

class FragmentMoviesList : BaseFragment(R.layout.fragment_movies_list) {

    private val binding
        get() = viewBinding!! as FragmentMoviesListBinding

    private val itemClickListener = object : MovieAdapter.OnItemClickListener {
        override fun onItemClick(movieId: Int) {
            navigateToMovieDetails(movieId)
        }
    }

    private val movieAdapter by lazy {
        MovieAdapter(
            { old, new -> old.isSame(new) },
            itemClickListener
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMoviesListBinding.bind(view)

        initMoviesList()
    }

    override fun onStart() {
        super.onStart()

        movieAdapter.submitList(MoviesDataSource().movies)
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

    private fun navigateToMovieDetails(movieId: Int) {
        val bundle = bundleOf(FragmentMovieDetails.ARG_MOVIE_ID to movieId)
        parentFragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace<FragmentMovieDetails>(R.id.fragment_container, args = bundle)
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}