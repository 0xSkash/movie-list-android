package de.skash.movielist.feature.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.skash.movielist.core.adapter.TvShowListAdapter
import de.skash.movielist.databinding.FragmentTvShowsBinding

@AndroidEntryPoint
class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowsBinding? = null
    private val binding: FragmentTvShowsBinding get() = _binding!!

    private val viewModel: TvShowViewModel by viewModels()
    private val tvShowListAdapter = TvShowListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTvShowsBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTvShowsBinding.bind(view)
        binding.tvShowsRecyclerView.adapter = tvShowListAdapter

        viewModel.tvShowsPagingDataLiveData.observe(viewLifecycleOwner) { pagingData ->
            tvShowListAdapter.submitData(lifecycle, pagingData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}