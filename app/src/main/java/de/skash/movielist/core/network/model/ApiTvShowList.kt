package de.skash.movielist.core.network.model

data class ApiTvShowList(
    val page: Int,
    val results: List<ApiTvShow>,
    val total_pages: Int,
    val total_results: Int
)