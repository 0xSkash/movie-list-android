package de.skash.movielist.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.skash.movielist.R
import de.skash.movielist.core.model.Movie
import de.skash.movielist.databinding.ListItemMovieSmallBinding

class SmallMovieListAdapter(private val onMovieClicked: (Movie) -> Unit) :
    PagingDataAdapter<Movie, SmallMovieViewHolder>(MovieDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallMovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieSmallBinding.inflate(layoutInflater, parent, false)
        return SmallMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SmallMovieViewHolder, position: Int) {
        val movie = getItem(position) ?: return
        holder.bind(movie)
        holder.binding.cardView.setOnClickListener {
            onMovieClicked(movie)
        }
    }
}

class SmallMovieViewHolder(
    val binding: ListItemMovieSmallBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.movieTitleTextView.text = movie.title
        Glide.with(binding.movieImageView)
            .load(movie.imageURL)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .centerCrop()
            .into(binding.movieImageView)
    }
}