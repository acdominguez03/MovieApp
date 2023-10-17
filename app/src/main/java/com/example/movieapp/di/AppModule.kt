package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.local.LocalDataSource
import com.example.movieapp.data.local.MoviesDatabase
import com.example.movieapp.data.local.dao.MoviesDao
import com.example.movieapp.data.remote.MovieService
import com.example.movieapp.data.remote.RemoteDataSource
import com.example.movieapp.data.repository.MoviesRepositoryImpl
import com.example.movieapp.domain.repository.MoviesRepository
import com.example.movieapp.domain.usecase.MoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // El singleton sirve para que si cuentas con varios repositorios que
    // implementarán este servicio, ambos accederán a la misma instancia del mismo
    // Si no pusiesemos el @Singleton se crearía una instancia por cada repositorio que accediese aquí
    @Provides
    @Singleton
    fun provideMovieService(): MovieService {
        // interceptor
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesDatabase(@ApplicationContext appContext: Context): MoviesDatabase {
        return Room.databaseBuilder(
            appContext,
            MoviesDatabase::class.java,
            "movies.db",
        ).build()
    }

    // Data sources
    @Provides
    @Singleton
    fun providesLocalDataSource(moviesDao: MoviesDao): LocalDataSource {
        return LocalDataSource(dao = moviesDao)
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(movieService: MovieService): RemoteDataSource {
        return RemoteDataSource(movieService = movieService)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
    ): MoviesRepository {
        return MoviesRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao {
        return moviesDatabase.moviesDao()
    }

    @Provides
    @Singleton
    fun provideMoviesUseCase(
        moviesRepository: MoviesRepository,
    ): MoviesUseCase {
        return MoviesUseCase(moviesRepository = moviesRepository)
    }
}
