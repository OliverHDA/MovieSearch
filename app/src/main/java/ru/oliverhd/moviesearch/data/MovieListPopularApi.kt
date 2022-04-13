package ru.oliverhd.moviesearch.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.oliverhd.moviesearch.model.MovieListServerResponse

interface MovieListPopularApi {

    @GET("3/movie/popular")
    fun getMovieListPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<MovieListServerResponse>
}