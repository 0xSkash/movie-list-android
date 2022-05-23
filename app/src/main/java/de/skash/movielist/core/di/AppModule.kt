package de.skash.movielist.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.skash.movielist.BuildConfig
import de.skash.movielist.core.network.api.MovieApi
import de.skash.movielist.core.network.api.PeopleApi
import de.skash.movielist.core.repository.ApiMovieRepository
import de.skash.movielist.core.repository.ApiPeopleRepository
import de.skash.movielist.core.repository.MovieRepository
import de.skash.movielist.core.repository.PeopleRepository
import de.skash.movielist.core.util.Constants
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(createHttpClient())
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    @Singleton
    @Provides
    fun provideMovieRepository(movieApi: MovieApi): MovieRepository {
        return ApiMovieRepository(movieApi)
    }

    @Singleton
    @Provides
    fun providePeopleRepository(peopleApi: PeopleApi): PeopleRepository {
        return ApiPeopleRepository(peopleApi)
    }

    @Singleton
    @Provides
    fun provideMovieApi(): MovieApi {
        return retrofit
            .create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun providePeopleApi(): PeopleApi {
        return retrofit
            .create(PeopleApi::class.java)
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(Constants.API_KEY_QUERY_PARAMETER_NAME, BuildConfig.API_KEY)
                    .build()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)

                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }
}