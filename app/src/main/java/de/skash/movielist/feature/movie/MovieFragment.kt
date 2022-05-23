package de.skash.movielist.feature.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import de.skash.movielist.R
import de.skash.movielist.core.adapter.MovieViewPagerAdapter
import de.skash.movielist.databinding.FragmentMoviesBinding

class MovieFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding: FragmentMoviesBinding get() = _binding!!

    private lateinit var viewPagerAdapter: MovieViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMoviesBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMoviesBinding.bind(view)
        viewPagerAdapter = MovieViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setText(getTitleForViewPagerPosition(position))
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTitleForViewPagerPosition(position: Int): Int {
        return when (position) {
            0 -> R.string.title_trending_movies
            1 -> R.string.title_popular_movies
            2 -> R.string.title_upcoming_movies
            else -> throw RuntimeException("Unknown ViewPager page provided: $position")
        }
    }
}