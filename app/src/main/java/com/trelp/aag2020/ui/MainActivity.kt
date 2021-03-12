package com.trelp.aag2020.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.trelp.aag2020.R
import com.trelp.aag2020.data.Movie
import com.trelp.aag2020.ui.details.FragmentMovieDetails
import com.trelp.aag2020.ui.movies.FragmentMoviesList

class MainActivity : AppCompatActivity(R.layout.activity_main),
    FragmentMoviesList.OnItemClickListener,
    FragmentMovieDetails.OnBackButtonClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateToMoviesList(savedInstanceState == null)
    }

    private fun navigateToMoviesList(isSaveState: Boolean) {
        if (isSaveState) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<FragmentMoviesList>(R.id.fragment_container)
            }
        }
    }

    private fun navigateToMovieDetails(movie: Movie) {
        val bundle = bundleOf(FragmentMovieDetails.ARG_MOVIE to movie)
        supportFragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace<FragmentMovieDetails>(R.id.fragment_container, args = bundle)
        }
    }

    private fun backToPreviousFragment() {
        supportFragmentManager.popBackStack()
    }

    override fun onItemClick(movie: Movie) {
        navigateToMovieDetails(movie)
    }

    override fun onBackButtonClick() {
        backToPreviousFragment()
    }
}