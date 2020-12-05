package com.trelp.aag2020.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.trelp.aag2020.R
import com.trelp.aag2020.ui.movies.FragmentMoviesList

class MainActivity : AppCompatActivity(R.layout.activity_main) {

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
}