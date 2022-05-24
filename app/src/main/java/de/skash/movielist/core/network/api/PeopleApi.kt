package de.skash.movielist.core.network.api

import de.skash.movielist.core.network.model.ApiDetailedMovie
import de.skash.movielist.core.network.model.ApiDetailedPerson
import de.skash.movielist.core.network.model.ApiPeopleList
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {

    @GET("person/popular")
    fun getPopularPeople(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<ApiPeopleList>

    @GET("person/{personId}")
    fun getPersonDetailed(
        @Path("personId") personId: Int
    ): Single<ApiDetailedPerson>
}