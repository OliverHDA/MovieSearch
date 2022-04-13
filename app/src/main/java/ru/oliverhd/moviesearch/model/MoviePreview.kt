package ru.oliverhd.moviesearch.model

import com.google.gson.annotations.SerializedName

data class MoviePreview(
    @SerializedName("genre_ids")
    val genre_ids: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val rating: Double
)