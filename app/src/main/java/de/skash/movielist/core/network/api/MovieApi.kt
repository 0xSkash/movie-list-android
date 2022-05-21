package de.skash.movielist.core.network.api

import de.skash.movielist.core.network.model.ApiDetailedMovie
import de.skash.movielist.core.network.model.ApiMovieList
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ApiMovieList
    
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ApiMovieList

    @GET("movie/{movieId}")
    suspend fun getMovieDetailed(
        @Query("movieId") movieId: Int
    ): ApiDetailedMovie

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ApiMovieList
}