package de.skash.movielist.feature.people.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import de.skash.movielist.R
import de.skash.movielist.core.model.DetailedPerson
import de.skash.movielist.databinding.FragmentDetailedPersonBinding

@AndroidEntryPoint
class DetailedPersonFragment : Fragment() {

    private var _binding: FragmentDetailedPersonBinding? = null
    private val binding: FragmentDetailedPersonBinding get() = _binding!!

    private val viewModel: DetailedPersonViewModel by viewModels()
    private val navArgs by navArgs<DetailedPersonFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailedPersonBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailedPersonBinding.bind(view)
        viewModel.fetchDetailedPersonForId(navArgs.personId)

        viewModel.personLiveData.observe(viewLifecycleOwner) { person ->
            setupUI(person)
        }
    }

    private fun setupUI(person: DetailedPerson) {
        //TODO: Recylcer View for "Known For"
        binding.personNameTextView.text = person.name
        binding.personBiographyTextView.text = person.biography

        Glide.with(binding.personImageView)
            .load(person.imageURL)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .centerCrop()
            .into(binding.personImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}