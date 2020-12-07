package com.trelp.aag2020.ui.movies

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.GridLayoutManager
import com.trelp.aag2020.R
import com.trelp.aag2020.data.MoviesDataSource
import com.trelp.aag2020.databinding.FragmentMoviesListBinding
import com.trelp.aag2020.ui.common.BaseFragment
import com.trelp.aag2020.ui.details.FragmentMovieDetails

class FragmentMoviesList : BaseFragment(R.layout.fragment_movies_list) {

    private val binding
        get() = viewBinding!! as FragmentMoviesListBinding

    private val dataSource = MoviesDataSource()

    private val itemClickListener = object : MovieAdapter.OnItemClickListener {
        override fun onItemClick(movieId: Int) {
            navigateToMovieDetails(movieId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMoviesListBinding.bind(view)

        with(binding.listMovie) {
            adapter = MovieAdapter(itemClickListener).also { it.setupData(dataSource.movies) }
            addItemDecoration(
                MovieOffsetItemDecoration(
                    resources.getDimensionPixelOffset(R.dimen.item_movie_offset)
                )
            )
            layoutManager = GridLayoutManager(view.context, SPAN_COUNT)
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