package de.skash.movielist.feature.movie.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.skash.movielist.core.adapter.MovieListAdapter
import de.skash.movielist.databinding.FragmentTrendingMoviesBinding

@AndroidEntryPoint
class TrendingMoviesFragment : Fragment() {

    private var _binding: FragmentTrendingMoviesBinding? = null
    private val binding: FragmentTrendingMoviesBinding get() = _binding!!

    private val movieListAdapter = MovieListAdapter()
    private val viewModel: TrendingMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTrendingMoviesBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTrendingMoviesBinding.bind(view)
        binding.movieRecyclerView.adapter = movieListAdapter

        viewModel.moviePagingDataLiveData.observe(viewLifecycleOwner) { pagingData ->
            movieListAdapter.submitData(lifecycle, pagingData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}