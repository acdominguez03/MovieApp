package com.example.movieapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.entity.Movie
import com.example.movieapp.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: MoviesUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<UiState> by lazy { MutableStateFlow(UiState()) }
    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            _state.value = UiState(
                loading = true,
            )
            useCase.requestMovies()

            useCase.movies.collect {
                _state.value = UiState(
                    movies = it,
                )
            }
        }
    }

    fun onMovieClick(movie: Movie) {
        viewModelScope.launch {
            useCase.updateMovie(movie.copy(favorite = !movie.favorite))
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList(),
        val errorMessage: String? = null,
    )
}
