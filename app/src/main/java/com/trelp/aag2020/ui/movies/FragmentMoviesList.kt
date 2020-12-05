package com.trelp.aag2020.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.trelp.aag2020.R
import com.trelp.aag2020.databinding.FragmentMoviesListBinding
import com.trelp.aag2020.ui.common.BaseFragment
import com.trelp.aag2020.ui.details.FragmentMovieDetails

class FragmentMoviesList : BaseFragment(R.layout.fragment_movies_list) {

    private val binding
        get() = viewBinding!! as FragmentMoviesListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMoviesListBinding.bind(view)

        binding.itemMovie.root.setOnClickListener { navigateToMovieDetails() }
    }

    private fun navigateToMovieDetails() {
        parentFragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace<FragmentMovieDetails>(R.id.fragment_container)
        }
    }
}