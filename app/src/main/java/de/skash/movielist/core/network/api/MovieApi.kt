package de.skash.movielist.core.network.api

import de.skash.movielist.core.network.model.ApiDetailedMovie
import de.skash.movielist.core.network.model.ApiMovieList
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<ApiMovieList>

    @GET("trending/movie/day")
    fun getTrendingMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<ApiMovieList>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<ApiMovieList>

    @GET("movie/{movieId}")
    fun getMovieDetailed(
        @Path("movieId") movieId: Int
    ): Observable<ApiDetailedMovie>


}