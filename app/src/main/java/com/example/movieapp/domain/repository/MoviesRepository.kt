package com.example.movieapp.domain.repository

import com.example.movieapp.data.local.entity.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getAllMovies(): Flow<List<Movie>>

    suspend fun updateMovie(movie: Movie)

    suspend fun requestMovies()
}