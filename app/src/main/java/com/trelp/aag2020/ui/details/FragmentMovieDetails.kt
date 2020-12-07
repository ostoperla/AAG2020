package com.trelp.aag2020.ui.details

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trelp.aag2020.R
import com.trelp.aag2020.data.MovieDetailsDataSource
import com.trelp.aag2020.databinding.FragmentMovieDetailsBinding
import com.trelp.aag2020.ui.common.BaseFragment

class FragmentMovieDetails : BaseFragment(R.layout.fragment_movie_details) {

    private val binding
        get() = viewBinding!! as FragmentMovieDetailsBinding

    private var movieId: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.let {
            movieId = it.getInt(ARG_MOVIE_ID, 0)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMovieDetailsBinding.bind(view)

        val movieDetails = MovieDetailsDataSource().getMovieDetails(movieId)

        with(binding) {
            imageMovieLogo.setImageResource(movieDetails.poster)
            textMovieRatingSystem.text = movieDetails.ageLimit
            textMovieName.text = movieDetails.title
            textMovieTags.text = movieDetails.tags
            textMovieReviews.text = resources.getQuantityString(
                R.plurals.movie_reviews,
                movieDetails.reviewCount,
                movieDetails.reviewCount
            )
            textMovieStorylineContent.text = movieDetails.overview
        }

        with(binding.listActor) {
            adapter = ActorAdapter().also { it.setupData(movieDetails.actors) }
            addItemDecoration(
                DividerItemDecoration(context, RecyclerView.HORIZONTAL).also {
                    it.setSpaceDrawable(
                        resources.getDimensionPixelOffset(R.dimen.item_actor_offset),
                        0
                    )
                }
            )
            layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun DividerItemDecoration.setSpaceDrawable(@Px width: Int, @Px height: Int) {
        setDrawable(
            object : ColorDrawable() {
                override fun getIntrinsicWidth() = width
                override fun getIntrinsicHeight() = height
            }
        )
    }

    companion object {
        const val ARG_MOVIE_ID = "arg_movie_id"
    }
}