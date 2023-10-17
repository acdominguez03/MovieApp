package com.example.movieapp.data.remote

import com.example.movieapp.data.local.entity.Movie

data class ServerMovie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Long>,
    val id: Long,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Long,
    val favorite: Boolean = false
)

fun ServerMovie.toMovie(): Movie = Movie(
    id = 0,
    title = title,
    overview = overview,
    posterPath = poster_path,
    favorite = favorite
)