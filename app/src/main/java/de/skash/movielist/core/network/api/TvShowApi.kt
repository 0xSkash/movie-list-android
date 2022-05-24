package de.skash.movielist.core.network.api

import de.skash.movielist.core.network.model.ApiDetailedPerson
import de.skash.movielist.core.network.model.ApiDetailedTvShow
import de.skash.movielist.core.network.model.ApiTvShowList
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApi {
    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<ApiTvShowList>

    @GET("tv/{tvId}")
    fun getTvShowDetailed(
        @Path("tvId") tvId: Int
    ): Single<ApiDetailedTvShow>
}