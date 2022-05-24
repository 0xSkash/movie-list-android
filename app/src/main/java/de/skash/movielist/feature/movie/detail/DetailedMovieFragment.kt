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
import dagger.hilt.android.AndroidEntryPoint
import de.skash.movielist.core.model.DetailedMovie
import de.skash.movielist.core.util.Result
import de.skash.movielist.core.util.showErrorDialog
import de.skash.movielist.databinding.FragmentDetailedMovieBinding

@AndroidEntryPoint
class DetailedMovieFragment : Fragment() {

    private var _binding: FragmentDetailedMovieBinding? = null
    private val binding: FragmentDetailedMovieBinding get() = _binding!!

    private val args by navArgs<DetailedMovieFragmentArgs>()

    private val viewModel: DetailedMovieViewModel by viewModels()

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

        viewModel.movieLivedata.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> requireContext().showErrorDialog(result.errorType, onDismiss = {
                    findNavController().navigateUp()
                })
                is Result.Loading -> Log.d(javaClass.name, "Loading Detailed Movie...")
                is Result.Success -> setupUI(result.value)
            }
        }
    }


    private fun setupUI(movie: DetailedMovie) {
        //TODO: Add UI
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}