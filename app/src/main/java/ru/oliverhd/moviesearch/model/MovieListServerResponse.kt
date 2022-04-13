package ru.oliverhd.moviesearch.model

import com.google.gson.annotations.SerializedName

data class MovieListServerResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MoviePreview>
)