package de.skash.movielist.core.model

import de.skash.movielist.core.network.model.ApiDetailedTvShow

data class DetailedTvShow(
    val id: Int
) {
    constructor(apiModel: ApiDetailedTvShow) : this(
        id = apiModel.id
    )
}