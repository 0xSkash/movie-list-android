package de.skash.movielist.core.model

import de.skash.movielist.BuildConfig
import de.skash.movielist.core.network.model.ApiDetailedMovie
import de.skash.movielist.core.network.model.ApiDetailedPerson

data class DetailedPerson(
    val id: Int,
    val name: String,
    val biography: String,
    val imageURL: String
) {

    constructor(apiModel: ApiDetailedPerson) : this(
        id = apiModel.id,
        name = apiModel.name,
        biography = apiModel.biography,
        imageURL = "${BuildConfig.IMAGE_BASE_URL}${apiModel.profile_path}",
    )
}