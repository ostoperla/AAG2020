package com.trelp.aag2020.presentation.view.movies

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.trelp.aag2020.R
import com.trelp.aag2020.domain.entity.isSame
import com.trelp.aag2020.databinding.FragmentMoviesListBinding
import com.trelp.aag2020.di.ComponentOwner
import com.trelp.aag2020.di.Injector
import com.trelp.aag2020.di.activity.ActivityComponent
import com.trelp.aag2020.di.movies.MoviesComponent
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.presentation.view.common.BaseFragment
import com.trelp.aag2020.presentation.view.common.utils.dp2pxOffset
import com.trelp.aag2020.presentation.viewmodel.movies.MoviesListViewModel
import timber.log.Timber
import javax.inject.Inject

class FragmentMoviesList : BaseFragment(R.layout.fragment_movies_list),
    ComponentOwner<MoviesComponent> {

    private val binding
        get() = viewBinding!! as FragmentMoviesListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MoviesListViewModel by viewModels { viewModelFactory }

    interface OnItemClickListener {
        fun onItemClick(movieId: Int)
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

        Injector.getOrCreateComponent(this).inject(this)

        itemClickListener =
            if (context is OnItemClickListener) context
            else throw ClassCastException("${context.javaClass} must implement OnItemClickListener")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMoviesListBinding.bind(view)

        initMoviesList()

        viewModel.stateLiveData.observe(viewLifecycleOwner) { renderState(it) }

        with(binding) {
            swipeToRefresh.setOnRefreshListener { viewModel.refreshMovies() }
            emptyView.btnRefresh.setOnClickListener { viewModel.refreshMovies() }
        }
    }

    private fun updateMoviesList(data: List<Movie>) {
        movieAdapter.submitList(data)
    }

    private fun renderState(state: MoviesListViewModel.ViewState) {
        Timber.d(state.javaClass.simpleName)
        when (state) {
            MoviesListViewModel.ViewState.EmptyProgress -> with(binding) {
                listGroup.isVisible = false
                emptyView.root.isVisible = false
                listProgress.root.isVisible = true
                swipeToRefresh.isRefreshing = false
            }
            MoviesListViewModel.ViewState.Refresh -> with(binding) {
                listGroup.isVisible = true
                emptyView.root.isVisible = false
                listProgress.root.isVisible = false
                swipeToRefresh.isRefreshing = true
            }
            is MoviesListViewModel.ViewState.Data -> with(binding) {
                updateMoviesList(state.data)
                listGroup.isVisible = true
                emptyView.root.isVisible = false
                listProgress.root.isVisible = false
                swipeToRefresh.isRefreshing = false
            }
            MoviesListViewModel.ViewState.Empty -> with(binding) {
                listGroup.isVisible = false
                emptyView.root.isVisible = true
                listProgress.root.isVisible = false
                swipeToRefresh.isRefreshing = false
                emptyView.textTitle.text = "Ooooops!!!"         // TODO: 18.01.2021 поправить
                emptyView.textDescription.text = "Empty data"
            }
            is MoviesListViewModel.ViewState.Error -> with(binding) {
                listGroup.isVisible = false
                emptyView.root.isVisible = true
                listProgress.root.isVisible = false
                swipeToRefresh.isRefreshing = false
                emptyView.textTitle.text = "Ooooops!!!"         // TODO: 18.01.2021 поправить
                emptyView.textDescription.text = "Error ${state.error}"
            }
        }
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

    override fun onDetach() {
        itemClickListener = null

        super.onDetach()
    }

    override fun onDestroyView() {
        binding.listMovie.adapter = null

        super.onDestroyView()
    }

    override fun createComponent() =
        Injector.findComponent<ActivityComponent>().moviesComponentFactory().create()

    companion object {
        private const val SPAN_COUNT = 2
    }
}