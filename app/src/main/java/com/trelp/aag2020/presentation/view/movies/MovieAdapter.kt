package com.trelp.aag2020.presentation.view.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trelp.aag2020.R
import com.trelp.aag2020.domain.entity.Movie
import com.trelp.aag2020.databinding.ItemMovieNormalBinding
import com.trelp.aag2020.domain.entity.isSame
import com.trelp.aag2020.presentation.view.common.utils.context
import com.trelp.aag2020.presentation.view.common.utils.inflater
import com.trelp.aag2020.presentation.view.common.utils.loadImage
import com.trelp.aag2020.presentation.view.common.utils.tint
import com.trelp.aag2020.presentation.view.movies.MovieAdapter.MovieHolder

class MovieAdapter(
    private val itemClickListener: FragmentMoviesList.OnItemClickListener?
) : ListAdapter<Movie, MovieHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            ItemMovieNormalBinding.inflate(parent.inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        with(holder) {
            bind(getItem(position))
            itemView.setOnClickListener { itemClickListener?.onItemClick(getItem(position).id) }
        }
    }

    class MovieHolder(
        private val binding: ItemMovieNormalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                imageMovieLogo.loadImage(movie.poster)
                textMovieRatingSystem.text =
                    context.getString(R.string.movie_age_limit, movie.minimumAge)
                if (movie.id.rem(2) == 0) {
                    imageLike.drawable.tint(context, R.color.radical_red)
                } else {
                    imageLike.drawable.tint(context, R.color.white)
                }
                textMovieTags.text = movie.genres.joinToString { it.name }
                rating.rating = movie.ratings.div(2)
                textMovieReviews.text = itemView.resources.getQuantityString(
                    R.plurals.movie_reviews,
                    movie.runtime,
                    movie.runtime
                )
                textMovieName.text = movie.title
                textMovieDuration.text =
                    itemView.context.resources.getString(R.string.movie_duration, movie.runtime)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return if (oldItem === newItem) true else oldItem.isSame(newItem)
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}