package de.skash.movielist.core.util

import de.skash.movielist.R

sealed class ErrorType(val error: Int) {

    object NoInternet: ErrorType(R.string.error_type_no_internet)
    object MovieNotFound: ErrorType(R.string.error_type_movie_not_found)
    object FailedToLoadMovieList: ErrorType(R.string.error_type_failed_to_load_movie_list)

}