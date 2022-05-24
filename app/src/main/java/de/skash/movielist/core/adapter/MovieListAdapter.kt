package de.skash.movielist.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.skash.movielist.R
import de.skash.movielist.core.model.Movie
import de.skash.movielist.databinding.ListItemMovieBinding

class MovieListAdapter : PagingDataAdapter<Movie, MovieViewHolder>(MovieDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position) ?: return
        holder.bind(movie)
    }
}

class MovieViewHolder(
    private val binding: ListItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.movieTitleTextView.text = movie.title
        binding.movieOverviewTextView.text = movie.overview
        binding.movieReleaseDateTextView.text = movie.releaseDate
        Glide.with(binding.movieImageView)
            .load(movie.imageURL)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .centerCrop()
            .into(binding.movieImageView)
    }

}

class MovieDiffUtil : ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}