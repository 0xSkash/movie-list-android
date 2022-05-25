package de.skash.movielist.core.model

import de.skash.movielist.BuildConfig
import de.skash.movielist.core.network.model.ApiDetailedMovie

data class DetailedMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val imageURL: String,
    val genres: List<Genre>,
    val releaseDate: String,
    val budget: Int,
    val revenue: Int,
    val length: Int,
    val status: String,
    val originalLanguage: String
) {
    constructor(apiModel: ApiDetailedMovie) : this(
        id = apiModel.id,
        title = apiModel.title,
        overview = apiModel.overview,
        imageURL = "${BuildConfig.IMAGE_BASE_URL}${apiModel.poster_path}",
        genres = apiModel.genres.map {
            Genre(it)
        },
        releaseDate = apiModel.release_date,
        budget = apiModel.budget,
        revenue = apiModel.revenue,
        length = apiModel.runtime,
        status = apiModel.status,
        originalLanguage = apiModel.original_language
    )
}