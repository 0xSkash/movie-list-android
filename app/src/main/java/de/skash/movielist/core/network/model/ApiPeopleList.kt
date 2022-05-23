package de.skash.movielist.core.network.model

data class ApiPeopleList(
    val page: Int,
    val results: List<ApiPerson>,
    val total_pages: Int,
    val total_results: Int
)