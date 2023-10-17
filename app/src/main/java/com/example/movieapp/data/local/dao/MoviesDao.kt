package com.example.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieapp.data.local.entity.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM Movie")
    fun getMovies(): Flow<List<Movie>>

    @Update
    suspend fun updateMovie(movie: Movie)

    @Insert
    suspend fun insertAll(movies: List<Movie>)

    @Query("SELECT COUNT(*) FROM Movie")
    suspend fun count(): Int
}