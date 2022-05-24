package de.skash.movielist.core.model

import de.skash.movielist.core.network.model.ApiDetailedMovie

data class DetailedMovie(
    val id: Int,
    val title: String,
    val overview: String
) {
    constructor(apiModel: ApiDetailedMovie): this(
        id = apiModel.id,
        title = apiModel.title,
        overview = apiModel.overview
    )
}