package com.example.movieapp.data.repository

import android.util.Log
import com.example.movieapp.data.local.LocalDataSource
import com.example.movieapp.data.local.entity.Movie
import com.example.movieapp.data.remote.RemoteDataSource
import com.example.movieapp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.io.IOException

class MoviesRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MoviesRepository {
    override fun getAllMovies(): Flow<List<Movie>> = flow {
        try {
            emitAll(localDataSource.movies)
        } catch (e: IOException) {
            Log.d("MY_TAG", "Error al obtener las películas de la BBDD")
        }
    }

    override suspend fun updateMovie(movie: Movie) {
        try {
            localDataSource.updateMovie(movie = movie)
        } catch (e: IOException) {
            Log.d("MY_TAG", "Error al actualizar la película de la BBDD")
        }
    }

    override suspend fun requestMovies() {
        val isDbEmpty = localDataSource.count() == 0
        if (isDbEmpty) {
            localDataSource.insertAll(remoteDataSource.getMovies())
        }
    }
}