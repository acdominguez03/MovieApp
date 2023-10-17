package com.example.movieapp.data.remote

import com.example.movieapp.data.local.entity.Movie

class RemoteDataSource(
    private val movieService: MovieService
) {
    suspend fun getMovies(): List<Movie> {
        return movieService.getMovies().results.map {
            it.toMovie()
        }
    }
}