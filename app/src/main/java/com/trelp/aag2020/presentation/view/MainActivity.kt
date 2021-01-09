package com.trelp.aag2020.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.trelp.aag2020.R
import com.trelp.aag2020.di.ComponentOwner
import com.trelp.aag2020.di.Injector
import com.trelp.aag2020.di.activity.ActivityComponent
import com.trelp.aag2020.di.application.AppComponent
import com.trelp.aag2020.presentation.view.details.FragmentMovieDetails
import com.trelp.aag2020.presentation.view.movies.FragmentMoviesList
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main),
    FragmentMoviesList.OnItemClickListener,
    FragmentMovieDetails.OnBackButtonClick,
    ComponentOwner<ActivityComponent> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TmdbViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AAG2020)

        Injector.getOrCreateComponent(this).inject(this)

        super.onCreate(savedInstanceState)

        viewModel.config.observe(this) {
            navigateToMoviesList(savedInstanceState == null)
        }
    }

    private fun navigateToMoviesList(isSaveState: Boolean) {
        if (isSaveState) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<FragmentMoviesList>(R.id.fragment_container)
            }
        }
    }

    private fun navigateToMovieDetails(movieId: Int) {
        val bundle = bundleOf(FragmentMovieDetails.ARG_MOVIE_ID to movieId)
        supportFragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace<FragmentMovieDetails>(R.id.fragment_container, args = bundle)
        }
    }

    private fun backToPreviousFragment() {
        supportFragmentManager.popBackStack()
    }

    override fun onItemClick(movieId: Int) {
        navigateToMovieDetails(movieId)
    }

    override fun onBackButtonClick() {
        backToPreviousFragment()
    }

    override fun createComponent() =
        Injector.findComponent<AppComponent>().activityComponentFactory().create()
}