package com.trelp.aag2020.ui.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trelp.aag2020.R
import com.trelp.aag2020.data.Movie
import com.trelp.aag2020.data.isSame
import com.trelp.aag2020.databinding.ItemMovieNormalBinding
import com.trelp.aag2020.ui.common.utils.context
import com.trelp.aag2020.ui.common.utils.inflater
import com.trelp.aag2020.ui.common.utils.tint
import com.trelp.aag2020.ui.movies.MovieAdapter.MovieHolder

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
                imageMovieLogo.setImageResource(movie.posterSmall)
                textMovieRatingSystem.text = movie.ageLimit
                textMovieTags.text = movie.tags
                if (movie.isLike) {
                    imageLike.drawable.tint(context, R.color.radical_red)
                } else {
                    imageLike.drawable.tint(context, R.color.white)
                }
                textMovieReviews.text = itemView.resources.getQuantityString(
                    R.plurals.movie_reviews,
                    movie.reviewCount,
                    movie.reviewCount
                )
                rating.rating = movie.rating
                textMovieName.text = movie.title
                textMovieDuration.text =
                    itemView.context.resources.getString(R.string.movie_duration, movie.duration)
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