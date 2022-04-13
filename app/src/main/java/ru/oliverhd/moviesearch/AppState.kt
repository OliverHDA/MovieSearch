package ru.oliverhd.moviesearch

import ru.oliverhd.moviesearch.model.MovieDetails
import ru.oliverhd.moviesearch.model.MoviePreview

sealed class AppState {
    data class SuccessList(val movieData: List<MoviePreview>) : AppState()
    data class SuccessDetail(val movieData: MovieDetails) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}