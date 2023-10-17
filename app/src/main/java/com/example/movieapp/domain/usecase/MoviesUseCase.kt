package com.example.movieapp.domain.usecase

import com.example.movieapp.data.local.entity.Movie
import com.example.movieapp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesUseCase(
    private val moviesRepository: MoviesRepository
) {
    val movies: Flow<List<Movie>> = moviesRepository.getAllMovies()

    suspend fun updateMovie(movie: Movie) {
        moviesRepository.updateMovie(movie)
    }

    suspend fun requestMovies() {
        moviesRepository.requestMovies()
    }
}