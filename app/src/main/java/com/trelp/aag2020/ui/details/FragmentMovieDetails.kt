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
import com.trelp.aag2020.ui.common.utils.dp2pxSize

class FragmentMovieDetails : BaseFragment(R.layout.fragment_movie_details) {

    private val binding
        get() = viewBinding!! as FragmentMovieDetailsBinding

    private var movieId: Int = 0

    private val movieDetails by lazy { MovieDetailsDataSource().getMovieDetails(movieId) }
    private val actorAdapter by lazy { ActorAdapter() }

    private var backButtonClickListener: OnBackButtonClick? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.let {
            movieId = it.getInt(ARG_MOVIE_ID, 0)
        }

        backButtonClickListener =
            if (context is OnBackButtonClick) context
            else throw ClassCastException("${context.javaClass} must implement OnBackButtonClick")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMovieDetailsBinding.bind(view)

        with(binding) {
            imageMovieLogo.setImageResource(movieDetails.poster)
            binding.textMovieBack.setOnClickListener { backButtonClickListener?.onBackButtonClick() }
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

        initActorsList()
    }

    override fun onStart() {
        super.onStart()

        actorAdapter.setupData(movieDetails.actors)
    }

    private fun initActorsList() {
        with(binding.listActor) {
            adapter = actorAdapter
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL).also {
                    it.setSpaceDrawable(
                        context.dp2pxSize(R.dimen.item_actor_offset),
                        0
                    )
                }
            )
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
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

    override fun onDetach() {
        backButtonClickListener = null

        super.onDetach()
    }

    interface OnBackButtonClick {
        fun onBackButtonClick()
    }
}