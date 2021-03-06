package de.skash.movielist.core.model

import de.skash.movielist.BuildConfig
import de.skash.movielist.core.network.model.ApiMovie

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val imageURL: String,
    val releaseDate: String
) {
    constructor(apiModel: ApiMovie) : this(
        id = apiModel.id,
        title = apiModel.title,
        overview = apiModel.overview,
        imageURL = "${BuildConfig.IMAGE_BASE_URL}${apiModel.poster_path}",
        releaseDate = apiModel.release_date
    )
}
