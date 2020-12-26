package com.trelp.aag2020.presentation.view.details

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.annotation.Px
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trelp.aag2020.R
import com.trelp.aag2020.domain.entity.Actor
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.databinding.FragmentMovieDetailsBinding
import com.trelp.aag2020.presentation.view.common.BaseFragment
import com.trelp.aag2020.presentation.view.common.utils.dp2pxSize
import com.trelp.aag2020.presentation.view.common.utils.loadImage
import com.trelp.aag2020.presentation.viewmodel.details.MovieDetailsViewModel

class FragmentMovieDetails : BaseFragment(R.layout.fragment_movie_details) {

    private val binding
        get() = viewBinding!! as FragmentMovieDetailsBinding

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModel.factory(movie!!)
    }

    private var movie: Movie? = null

    private val actorAdapter by lazy { ActorAdapter() }
    private var backButtonClickListener: OnBackButtonClick? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.let {
            movie = it.getParcelable(ARG_MOVIE)
        }

        backButtonClickListener =
            if (context is OnBackButtonClick) context
            else throw ClassCastException("${context.javaClass} must implement OnBackButtonClick")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMovieDetailsBinding.bind(view)

        with(binding) {
            movie?.let {
                imageMovieLogo.loadImage(it.backdrop)
                textMovieBack.setOnClickListener { backButtonClickListener?.onBackButtonClick() }
                textMovieRatingSystem.text = it.minimumAge.toString()
                textMovieName.text = it.title
                textMovieTags.text = it.genres.joinToString { genre -> genre.name }
                textMovieReviews.text = resources.getQuantityString(
                    R.plurals.movie_reviews,
                    it.numberOfRatings,
                    it.numberOfRatings
                )
                textMovieStorylineContent.text = it.overview
            }
        }

        initActorsList()

        viewModel.actors.observe(viewLifecycleOwner) { setupActors(it) }
    }

    private fun setupActors(data: List<Actor>) {
        if (data.isNotEmpty()) {
            actorAdapter.setupData(data)
        } else {
            with(binding) {
                textMovieCast.isVisible = false
                listActor.isVisible = false
            }
        }
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
        const val ARG_MOVIE = "arg_movie"
    }

    override fun onDetach() {
        backButtonClickListener = null

        super.onDetach()
    }

    interface OnBackButtonClick {
        fun onBackButtonClick()
    }
}