package com.example.movieapp

import com.example.movieapp.data.local.LocalDataSource
import com.example.movieapp.data.local.entity.Movie
import com.example.movieapp.data.remote.RemoteDataSource
import com.example.movieapp.data.repository.MoviesRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyBlocking

class MoviesRepositoryImplTest {

    @Test
    fun `When DB is empty, server is called`() {
        val localDataSource = mock<LocalDataSource>() {
            onBlocking { count() } doReturn 0
        }
        val remoteDataSource = mock<RemoteDataSource>()
        val repositoryImpl = MoviesRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
        )

        runBlocking { repositoryImpl.requestMovies() }

        verifyBlocking(remoteDataSource) { getMovies() }
    }

    @Test
    fun `When DB is empty, movies are saved into DB`() {
        val expectedMovies = listOf(
            Movie(
                id = 1,
                title = "The Shawshank Redemption",
                overview = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                posterPath = "/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
                favorite = false,
            ),
        )
        val localDataSource = mock<LocalDataSource>() {
            onBlocking { count() } doReturn 0
        }
        val remoteDataSource = mock<RemoteDataSource>() {
            onBlocking { getMovies() } doReturn expectedMovies
        }
        val repositoryImpl = MoviesRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
        )

        runBlocking { repositoryImpl.requestMovies() }

        verifyBlocking(localDataSource) { insertAll(any()) }
    }
}
