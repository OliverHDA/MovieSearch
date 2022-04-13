package ru.oliverhd.moviesearch.data

import retrofit2.Callback
import ru.oliverhd.moviesearch.BuildConfig
import ru.oliverhd.moviesearch.model.MovieDetails
import ru.oliverhd.moviesearch.model.MovieListServerResponse

class RepositoryImpl(private val retrofitImpl: RetrofitImpl) : Repository {

    override fun getMovieDetail(id: Int, callback: Callback<MovieDetails>) {
        retrofitImpl.getMovieDetail().getMovieInfo(id, BuildConfig.MOVIE_API_KEY, LANGUAGE_RU)
            .enqueue(callback)
    }

    override fun getPopularMovieList(callback: Callback<MovieListServerResponse>) {
        retrofitImpl.getPopularMovieList()
            .getMovieListPopular(BuildConfig.MOVIE_API_KEY, LANGUAGE_RU).enqueue(callback)
    }

    companion object {
        private const val LANGUAGE_RU = "ru"
    }
}