package com.example.movieapp.data.remote

import retrofit2.http.GET

interface MovieService {
    @GET("discover/movie?api_key=1418a8c194df4d8ce13a0d7fff8dfa03")
    suspend fun getMovies(): MovieResult
}