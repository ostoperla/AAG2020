package com.trelp.aag2020.presentation.view.details

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.Px
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trelp.aag2020.R
import com.trelp.aag2020.domain.entity.Actor
import com.trelp.aag2020.databinding.FragmentMovieDetailsBinding
import com.trelp.aag2020.di.ComponentOwner
import com.trelp.aag2020.di.Injector
import com.trelp.aag2020.di.activity.ActivityComponent
import com.trelp.aag2020.di.details.DetailsComponent
import com.trelp.aag2020.domain.MovieRepository
import com.trelp.aag2020.domain.entity.MovieDetails
import com.trelp.aag2020.presentation.view.common.BaseFragment
import com.trelp.aag2020.presentation.view.common.utils.dp2pxSize
import com.trelp.aag2020.presentation.view.common.utils.loadImage
import com.trelp.aag2020.presentation.viewmodel.details.MovieDetailsViewModel
import timber.log.Timber
import javax.inject.Inject

class FragmentMovieDetails : BaseFragment(R.layout.fragment_movie_details),
    ComponentOwner<DetailsComponent> {

    private val binding
        get() = viewBinding!! as FragmentMovieDetailsBinding

    @Inject
    lateinit var moviesRepository: MovieRepository

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModel.factory(moviesRepository, arguments?.getInt(ARG_MOVIE_ID) ?: 0)
    }

    private val actorAdapter by lazy { ActorAdapter() }
    private var backButtonClickListener: OnBackButtonClick? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Injector.getOrCreateComponent(this).inject(this)

        backButtonClickListener =
            if (context is OnBackButtonClick) context
            else throw ClassCastException("${context.javaClass} must implement OnBackButtonClick")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMovieDetailsBinding.bind(view)

        initActorsList()

        viewModel.movie.observe(viewLifecycleOwner) { renderState(it) }
//        viewModel.actors.observe(viewLifecycleOwner) { setupActors(it) }
    }

    private fun setupDetails(movie: MovieDetails?) {
        with(binding) {
            movie?.let {
                imageMovieLogo.loadImage(it.backdropPath)
                textMovieBack.setOnClickListener { backButtonClickListener?.onBackButtonClick() }
                textMovieRatingSystem.text = it.minimumAge.toString()
                textMovieName.text = it.title
                textMovieTags.text = it.genres.joinToString { genre -> genre.name }
                textMovieReviews.text = resources.getQuantityString(
                    R.plurals.movie_reviews,
                    it.voteCount,
                    it.voteCount
                )
                textMovieStorylineContent.text = it.overview
//                setupActors(it.actors)
            }
        }
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

    private fun renderState(state: MovieDetailsViewModel.ViewState) {
        Timber.d(state.javaClass.simpleName)
        when (state) {
            MovieDetailsViewModel.ViewState.Loading -> Toast.makeText(
                requireContext(),
                "Loading",
                Toast.LENGTH_SHORT
            ).show()
            is MovieDetailsViewModel.ViewState.Data -> {
                with(binding) {
                    imageMovieLogo.loadImage(state.data.backdropPath)
                    textMovieBack.setOnClickListener { backButtonClickListener?.onBackButtonClick() }
                    textMovieRatingSystem.text = state.data.minimumAge.toString()
                    textMovieName.text = state.data.title
                    textMovieTags.text = state.data.genres.joinToString { genre -> genre.name }
                    textMovieReviews.text = resources.getQuantityString(
                        R.plurals.movie_reviews,
                        state.data.voteCount,
                        state.data.voteCount
                    )
                    textMovieStorylineContent.text = state.data.overview
                }
                setupActors(state.data.actors)
            }
            is MovieDetailsViewModel.ViewState.Error -> Toast.makeText(
                requireContext(),
                "Error",
                Toast.LENGTH_SHORT
            ).show()
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

    override fun onDetach() {
        backButtonClickListener = null

        super.onDetach()
    }

    override fun createComponent() =
        Injector.findComponent<ActivityComponent>().detailsComponentFactory().create()

    interface OnBackButtonClick {
        fun onBackButtonClick()
    }

    companion object {
        const val ARG_MOVIE_ID = "arg_movie_id"
    }
}