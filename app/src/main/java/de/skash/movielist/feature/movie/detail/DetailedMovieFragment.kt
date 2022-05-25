package de.skash.movielist.feature.movie.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import de.skash.movielist.R
import de.skash.movielist.core.adapter.SmallMovieListAdapter
import de.skash.movielist.core.model.DetailedMovie
import de.skash.movielist.core.util.Result
import de.skash.movielist.core.util.showErrorDialog
import de.skash.movielist.core.util.toStringWithDefaultValue
import de.skash.movielist.databinding.FragmentDetailedMovieBinding

@AndroidEntryPoint
class DetailedMovieFragment : Fragment() {

    private var _binding: FragmentDetailedMovieBinding? = null
    private val binding: FragmentDetailedMovieBinding get() = _binding!!

    private val args by navArgs<DetailedMovieFragmentArgs>()

    private val viewModel: DetailedMovieViewModel by viewModels()

    private val movieListAdapter = SmallMovieListAdapter(onMovieClicked = {

    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailedMovieBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailedMovieBinding.bind(view)
        viewModel.fetchDetailedMovieForId(args.movieId)
        viewModel.fetchRecommendationsForId(args.movieId)
        binding.recommendedMoviesRecyclerView.adapter = movieListAdapter

        viewModel.movieLivedata.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> requireContext().showErrorDialog(result.errorType, onDismiss = {
                    findNavController().navigateUp()
                })
                is Result.Loading -> Log.d(javaClass.name, "Loading Detailed Movie...")
                is Result.Success -> setupUI(result.value)
            }
        }

        viewModel.recommendedMoviesLivedata.observe(viewLifecycleOwner) { pagingData ->
            movieListAdapter.submitData(lifecycle, pagingData)
        }
    }


    private fun setupUI(movie: DetailedMovie) {
        binding.movieTitleTextView.text = movie.title
        binding.movieOverviewTextView.text = movie.overview
        binding.movieStatusTextView.text = movie.status
        binding.movieOriginalLanguageTextView.text = movie.originalLanguage
        binding.movieBudgetTextView.text = movie.budget.toStringWithDefaultValue()
        binding.movieRevenueTextView.text = movie.revenue.toStringWithDefaultValue()

        Glide.with(binding.movieImageView)
            .load(movie.imageURL)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .centerCrop()
            .into(binding.movieImageView)
        //TODO: Add UI
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}