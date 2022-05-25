package de.skash.movielist.core.util

sealed class FilterType {
    class Recommendations(val movieId: Int): FilterType()
    class Similar(val movieId: Int): FilterType()
    object Popular: FilterType()
    object Trending: FilterType()
    object Upcoming: FilterType()
}
