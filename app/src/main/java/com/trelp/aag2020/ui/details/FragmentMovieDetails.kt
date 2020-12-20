package com.trelp.aag2020.ui.details

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.annotation.Px
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trelp.aag2020.R
import com.trelp.aag2020.data.Movie
import com.trelp.aag2020.databinding.FragmentMovieDetailsBinding
import com.trelp.aag2020.ui.common.BaseFragment
import com.trelp.aag2020.ui.common.utils.dp2pxSize
import com.trelp.aag2020.ui.common.utils.loadImage

class FragmentMovieDetails : BaseFragment(R.layout.fragment_movie_details) {

    private val binding
        get() = viewBinding!! as FragmentMovieDetailsBinding

    private var movie: Movie? = null

    private val actorAdapter by lazy { ActorAdapter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.let {
            movie = it.getParcelable(ARG_MOVIE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMovieDetailsBinding.bind(view)

        with(binding) {
            movie?.let {
                imageMovieLogo.loadImage(it.backdrop)
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
    }

    override fun onStart() {
        super.onStart()

        movie?.let {
            if (it.actors.isNotEmpty()) {
                actorAdapter.setupData(it.actors)
            } else {
                with(binding) {
                    textMovieCast.isVisible = false
                    listActor.isVisible = false
                }
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
}