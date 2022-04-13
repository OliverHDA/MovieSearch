package ru.oliverhd.moviesearch.data

import retrofit2.Callback
import ru.oliverhd.moviesearch.model.MovieDetails
import ru.oliverhd.moviesearch.model.MovieListServerResponse

interface Repository {
    fun getMovieDetail(id: Int, callback: Callback<MovieDetails>)
    fun getPopularMovieList(callback: Callback<MovieListServerResponse>)
}