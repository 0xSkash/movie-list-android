package de.skash.movielist.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.skash.movielist.R
import de.skash.movielist.core.model.TvShow
import de.skash.movielist.databinding.ListItemTvShowBinding

class TvShowListAdapter : PagingDataAdapter<TvShow, TvShowViewHolder>(TvShowDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemTvShowBinding.inflate(layoutInflater, parent, false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position) ?: return
        holder.bind(tvShow)
    }
}

class TvShowViewHolder(
    private val binding: ListItemTvShowBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tvShow: TvShow) {
        binding.tvShowNameTextView.text = tvShow.name
        binding.tvShowOverviewTextView.text = tvShow.overview
        Glide.with(binding.tvShowImageView)
            .load(tvShow.imageURL)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .centerCrop()
            .into(binding.tvShowImageView)
    }

}

class TvShowDiffUtil : DiffUtil.ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem == newItem
    }
}