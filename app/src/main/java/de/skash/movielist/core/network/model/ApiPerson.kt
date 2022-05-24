package de.skash.movielist.core.network.model

data class ApiPerson(
    val adult: Boolean,
    val id: Int,
    val known_for: List<ApiKnownFor>,
    val name: String,
    val popularity: Double,
    val profile_path: String
)