package com.trelp.aag2020.ui.movies

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.trelp.aag2020.R
import com.trelp.aag2020.TMDBApplication
import com.trelp.aag2020.data.Movie
import com.trelp.aag2020.data.isSame
import com.trelp.aag2020.databinding.FragmentMoviesListBinding
import com.trelp.aag2020.ui.common.BaseFragment
import com.trelp.aag2020.ui.common.utils.dp2pxOffset

class FragmentMoviesList : BaseFragment(R.layout.fragment_movies_list) {

    private val binding
        get() = viewBinding!! as FragmentMoviesListBinding

    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModel.factory(TMDBApplication.INSTANCE)
    }

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

        viewModel.movies.observe(viewLifecycleOwner) { updateMoviesList(it) }
    }

    private fun updateMoviesList(data: List<Movie>) {
        binding.listMovie.adapter = movieAdapter.also { it.submitList(data) }
    }

    private fun initMoviesList() {
        with(binding.listMovie) {
            setHasFixedSize(true)
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