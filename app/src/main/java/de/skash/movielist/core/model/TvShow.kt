package de.skash.movielist.core.model

import de.skash.movielist.BuildConfig
import de.skash.movielist.core.network.model.ApiTvShow


data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    val imageURL: String,
    val firstAirDate: String
) {

    constructor(apiModel: ApiTvShow) : this(
        id = apiModel.id,
        name = apiModel.name,
        overview = apiModel.overview,
        imageURL = "${BuildConfig.IMAGE_BASE_URL}${apiModel.poster_path}",
        firstAirDate = apiModel.first_air_date
    )
}