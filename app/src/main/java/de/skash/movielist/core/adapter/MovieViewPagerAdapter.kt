package de.skash.movielist.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import de.skash.movielist.feature.movie.popular.PopularMoviesFragment
import de.skash.movielist.feature.movie.trending.TrendingMoviesFragment
import de.skash.movielist.feature.movie.upcoming.UpcomingMoviesFragment

class MovieViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> TrendingMoviesFragment()
            1 -> PopularMoviesFragment()
            2 -> UpcomingMoviesFragment()
            else -> throw RuntimeException("Provide a position between 0 - $itemCount! Position provided: $position")
        }
    }

}