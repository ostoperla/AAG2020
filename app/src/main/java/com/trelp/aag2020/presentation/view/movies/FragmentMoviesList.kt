package com.trelp.aag2020.presentation.view.movies

import android.content.Context
import android.os.Bundle
import android.view.View
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

    override fun createComponent() =
        Injector.findComponent<ActivityComponent>().moviesComponentFactory().create()

    companion object {
        private const val SPAN_COUNT = 2
    }
}