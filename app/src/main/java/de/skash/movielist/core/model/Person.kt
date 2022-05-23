package de.skash.movielist.core.model

import de.skash.movielist.BuildConfig
import de.skash.movielist.core.network.model.ApiPerson

data class Person(
    val id: Int,
    val name: String,
    val imageURL: String
) {
    constructor(apiModel: ApiPerson): this(
        id = apiModel.id,
        name = apiModel.name,
        imageURL = "${BuildConfig.BASE_URL}${apiModel.profile_path}"
    )
}