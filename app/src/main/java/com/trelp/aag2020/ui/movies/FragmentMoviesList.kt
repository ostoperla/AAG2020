package com.trelp.aag2020.ui.movies

import android.content.Context
import android.os.Bundle
import android.view.View
import com.trelp.aag2020.R
import com.trelp.aag2020.databinding.FragmentMoviesListBinding
import com.trelp.aag2020.ui.common.BaseFragment

class FragmentMoviesList : BaseFragment(R.layout.fragment_movies_list) {

    private val binding
        get() = viewBinding!! as FragmentMoviesListBinding

    private var itemClickListener: OnItemClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        itemClickListener =
            if (context is OnItemClickListener) context
            else throw ClassCastException("${context.javaClass} must implement OnItemClickListener")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMoviesListBinding.bind(view)

        binding.itemMovie.root.setOnClickListener { itemClickListener?.onItemClick() }
    }

    override fun onDetach() {
        itemClickListener = null

        super.onDetach()
    }

    interface OnItemClickListener {
        fun onItemClick()
    }
}