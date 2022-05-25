package de.skash.movielist.core.model

import de.skash.movielist.core.network.model.ApiGenre

data class Genre(
    val id: Int,
    val name: String
) {
    constructor(apiModel: ApiGenre) : this(
        id = apiModel.id,
        name = apiModel.name

    )
}