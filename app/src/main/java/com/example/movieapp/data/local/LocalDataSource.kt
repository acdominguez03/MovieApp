package com.example.movieapp.data.local

import com.example.movieapp.data.local.dao.MoviesDao
import com.example.movieapp.data.local.entity.Movie
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val dao: MoviesDao
) {
    val movies: Flow<List<Movie>> = dao.getMovies()

    suspend fun updateMovie(movie: Movie) {
        dao.updateMovie(movie)
    }

    suspend fun insertAll(movies: List<Movie>) {
        dao.insertAll(movies)
    }

    suspend fun count(): Int {
        return dao.count()
    }
}