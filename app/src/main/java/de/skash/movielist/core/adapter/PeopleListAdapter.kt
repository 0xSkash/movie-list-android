package de.skash.movielist.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.skash.movielist.R
import de.skash.movielist.core.model.Person
import de.skash.movielist.databinding.ListItemPersonBinding

class PersonListAdapter : PagingDataAdapter<Person, PersonViewHolder>(PersonDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemPersonBinding.inflate(layoutInflater, parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = getItem(position) ?: return
        holder.bind(person)
    }
}

class PersonViewHolder(
    private val binding: ListItemPersonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(person: Person) {
        binding.personNameTextView.text = person.name
        binding.personKnownForTextView.text = person.knownFor.joinToString {
            it
        }
        Glide.with(binding.personImageView)
            .load(person.imageURL)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .centerCrop()
            .into(binding.personImageView)
    }

}

class PersonDiffUtil : ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}