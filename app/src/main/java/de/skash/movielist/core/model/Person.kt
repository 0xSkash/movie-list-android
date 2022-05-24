package de.skash.movielist.core.model

import de.skash.movielist.BuildConfig
import de.skash.movielist.core.network.model.ApiPerson

data class Person(
    val id: Int,
    val name: String,
    val imageURL: String,
    val knownFor: List<String>
) {
    constructor(apiModel: ApiPerson) : this(
        id = apiModel.id,
        name = apiModel.name,
        imageURL = "${BuildConfig.IMAGE_BASE_URL}${apiModel.profile_path}",
        knownFor = apiModel.known_for.map {
            it.original_title ?: it.name ?: ""
        }
    )
}