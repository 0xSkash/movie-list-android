package de.skash.movielist.feature.movie.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.skash.movielist.core.adapter.MovieListAdapter
import de.skash.movielist.databinding.FragmentPopularMoviesBinding
import de.skash.movielist.feature.movie.MovieFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding: FragmentPopularMoviesBinding get() = _binding!!

    private val movieListAdapter = MovieListAdapter(onMovieClicked = { movie ->
        viewModel.onMovieClicked(movie.id)
    })
    private val viewModel: PopularMoviesViewModel by viewModels()

    private val subscriptions = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPopularMoviesBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPopularMoviesBinding.bind(view)
        binding.movieRecyclerView.adapter = movieListAdapter

        viewModel.moviePagingDataLiveData.observe(viewLifecycleOwner) { pagingData ->
            movieListAdapter.submitData(lifecycle, pagingData)
        }

        viewModel.movieClickLiveData.observe(viewLifecycleOwner) { movieId ->
            (parentFragment as? MovieFragment)?.onMovieClicked(movieId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}