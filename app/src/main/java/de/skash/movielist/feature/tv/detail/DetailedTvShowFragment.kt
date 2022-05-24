package de.skash.movielist.feature.tv.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import de.skash.movielist.core.model.DetailedTvShow
import de.skash.movielist.databinding.FragmentDetailedTvShowBinding

@AndroidEntryPoint
class DetailedTvShowFragment : Fragment() {

    private var _binding: FragmentDetailedTvShowBinding? = null
    private val binding: FragmentDetailedTvShowBinding get() = _binding!!

    private val viewModel: DetailedTvShowViewModel by viewModels()
    private val navArgs by navArgs<DetailedTvShowFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailedTvShowBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailedTvShowBinding.bind(view)
        viewModel.fetchDetailedTvShowForId(navArgs.tvShowId)

        viewModel.tvShowLiveData.observe(viewLifecycleOwner) { tvShow ->
            setupUI(tvShow)
        }
    }

    private fun setupUI(tvShow: DetailedTvShow) {
        //TODO: UI
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}