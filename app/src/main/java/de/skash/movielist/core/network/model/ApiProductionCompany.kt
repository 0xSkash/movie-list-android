package de.skash.movielist.core.network.model

data class ApiProductionCompany(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)