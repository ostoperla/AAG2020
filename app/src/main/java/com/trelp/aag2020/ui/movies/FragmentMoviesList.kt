package com.trelp.aag2020.ui.movies

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.trelp.aag2020.R
import com.trelp.aag2020.data.MoviesDataSource
import com.trelp.aag2020.databinding.FragmentMoviesListBinding
import com.trelp.aag2020.ui.common.BaseFragment
import com.trelp.aag2020.ui.common.utils.dp2pxOffset

class FragmentMoviesList : BaseFragment(R.layout.fragment_movies_list) {

    private val binding
        get() = viewBinding!! as FragmentMoviesListBinding

    private var itemClickListener: OnItemClickListener? = null

    private val movieAdapter by lazy { MovieAdapter(itemClickListener) }

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

        movieAdapter.submitList(MoviesDataSource().movies)
    }

    override fun onDetach() {
        itemClickListener = null

        super.onDetach()
    }

    override fun onDestroyView() {
        binding.listMovie.adapter = null

        super.onDestroyView()
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

    interface OnItemClickListener {
        fun onItemClick(movieId: Int)
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}