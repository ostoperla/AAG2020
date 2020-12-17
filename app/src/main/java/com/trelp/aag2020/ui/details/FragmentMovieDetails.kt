package com.trelp.aag2020.ui.details

import android.content.Context
import android.os.Bundle
import android.view.View
import com.trelp.aag2020.R
import com.trelp.aag2020.databinding.FragmentMovieDetailsBinding
import com.trelp.aag2020.ui.common.BaseFragment

class FragmentMovieDetails : BaseFragment(R.layout.fragment_movie_details) {

    private val binding
        get() = viewBinding!! as FragmentMovieDetailsBinding

    private var backButtonClickListener: OnBackButtonClick? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backButtonClickListener =
            if (context is OnBackButtonClick) context
            else throw ClassCastException("${context.javaClass} must implement OnBackButtonClick")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMovieDetailsBinding.bind(view)

        binding.textMovieBack.setOnClickListener { backButtonClickListener?.onBackButtonClick() }
    }

    override fun onDetach() {
        backButtonClickListener = null

        super.onDetach()
    }

    interface OnBackButtonClick {
        fun onBackButtonClick()
    }
}