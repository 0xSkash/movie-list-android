package de.skash.movielist.core.repository

import de.skash.movielist.core.network.api.MovieApi
import javax.inject.Inject

class ApiMovieRepository @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {
}