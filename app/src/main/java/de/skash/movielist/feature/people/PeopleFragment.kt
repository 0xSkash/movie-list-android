package de.skash.movielist.feature.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.skash.movielist.core.adapter.PersonListAdapter
import de.skash.movielist.databinding.FragmentPeopleBinding

@AndroidEntryPoint
class PeopleFragment : Fragment() {

    private var _binding: FragmentPeopleBinding? = null
    private val binding: FragmentPeopleBinding get() = _binding!!

    private val peopleListAdapter = PersonListAdapter(onPersonClicked = { person ->
        viewModel.onPersonClicked(person.id)
    })
    private val viewModel: PeopleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPeopleBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPeopleBinding.bind(view)
        binding.peopleRecyclerView.adapter = peopleListAdapter

        viewModel.peoplePagingDataLiveData.observe(viewLifecycleOwner) { pagingData ->
            peopleListAdapter.submitData(lifecycle, pagingData)
        }

        viewModel.personClickLiveData.observe(viewLifecycleOwner) { personId ->
            val navAction = PeopleFragmentDirections.actionNavigationPeopleToDetailedPersonFragment(personId)
            findNavController().navigate(navAction)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}