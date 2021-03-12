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
import com.google.android.material.snackbar.Snackbar
import com.trelp.aag2020.R
import com.trelp.aag2020.databinding.FragmentMovieDetailsBinding
import com.trelp.aag2020.di.ComponentOwner
import com.trelp.aag2020.di.Injector
import com.trelp.aag2020.di.activity.ActivityComponent
import com.trelp.aag2020.di.details.DetailsComponent
import com.trelp.aag2020.domain.interactor.ActorInteractor
import com.trelp.aag2020.domain.interactor.MovieInteractor
import com.trelp.aag2020.presentation.view.common.BaseFragment
import com.trelp.aag2020.presentation.view.common.utils.dp2pxSize
import com.trelp.aag2020.presentation.view.common.utils.loadImage
import com.trelp.aag2020.presentation.viewmodel.details.ActorsListViewModel
import com.trelp.aag2020.presentation.viewmodel.details.MovieDetailsViewModel
import timber.log.Timber
import com.trelp.aag2020.presentation.viewmodel.details.MovieDetailsViewModelFactory
import javax.inject.Inject

class FragmentMovieDetails : BaseFragment(R.layout.fragment_movie_details),
    ComponentOwner<DetailsComponent> {

    private val binding
        get() = viewBinding!! as FragmentMovieDetailsBinding

    private var snackbar: Snackbar? = null

    @Inject
    lateinit var movieInteractor: MovieInteractor

    @Inject
    lateinit var actorInteractor: ActorInteractor

    private val actorsViewModel: ActorsListViewModel by viewModels {
        ActorsListViewModel.factory(actorInteractor, arguments?.getInt(ARG_MOVIE_ID) ?: 0)
    }

    private val detailsViewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory(movieInteractor, arguments?.getInt(ARG_MOVIE_ID) ?: 0)
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

        binding.textMovieBack.setOnClickListener { backButtonClickListener?.onBackButtonClick() }

        detailsViewModel.stateLiveData.observe(viewLifecycleOwner) { renderDetailsInfo(it) }
        actorsViewModel.stateLiveData.observe(viewLifecycleOwner) { renderActorsList(it) }
    }

    private fun renderActorsList(state: ActorsListViewModel.ViewState) {
        when (state) {
            ActorsListViewModel.ViewState.EmptyProgress -> with(binding) {
                listProgress.root.isVisible = true
                textMovieCast.isVisible = true
                listActor.isVisible = false
            }
            is ActorsListViewModel.ViewState.Data -> with(binding) {
                listProgress.root.isVisible = false
                listActor.isVisible = true
                actorAdapter.setupData(state.data)
            }
            ActorsListViewModel.ViewState.Empty -> with(binding) {
                listProgress.root.isVisible = false
                textMovieCast.isVisible = false
                listActor.isVisible = false
                snackbar = Snackbar.make(root, getString(R.string.empty_data), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.action_refresh)) { actorsViewModel.refreshActors() }
                snackbar?.show()
            }
            is ActorsListViewModel.ViewState.Error -> with(binding) {
                listProgress.root.isVisible = false
                textMovieCast.isVisible = false
                listActor.isVisible = false
                snackbar = Snackbar.make(root, getString(R.string.empty_error), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.action_refresh)) { actorsViewModel.refreshActors() }
                snackbar?.show()
            }
        }
    }

    private fun renderDetailsInfo(state: MovieDetailsViewModel.ViewState) {
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

    override fun onDestroyView() {
        snackbar?.dismiss()

        super.onDestroyView()
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