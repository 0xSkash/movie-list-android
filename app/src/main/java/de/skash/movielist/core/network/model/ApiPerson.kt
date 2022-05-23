package de.skash.movielist.core.network.model

import com.google.gson.annotations.SerializedName

data class ApiPerson(
    val adult: Boolean,
    val id: Int,
    val known_for: List<KnownFor>,
    val name: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profile_path: String
)