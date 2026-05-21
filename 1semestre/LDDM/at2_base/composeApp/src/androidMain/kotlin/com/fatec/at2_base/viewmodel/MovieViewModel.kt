package com.fatec.at2_base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.at2_base.model.Movie
import com.fatec.at2_base.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MovieUiState (
    val movies: List<Movie> = emptyList(),
    val inputTitle: String = "",
    val inputGenre: String = "",
) {
    val totalMovies: Int get() = movies.size
    val isEmpty: Boolean get() = movies.isEmpty()
}

class MovieViewModel (
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    init {
        carregarFilmes()
    }

    fun onTitleChange(newTitle: String) {
        _uiState.value = _uiState.value.copy(inputTitle = newTitle)
    }

    fun onGenreChange(newGenre: String) {
        _uiState.value = _uiState.value.copy(inputGenre = newGenre)
    }

    private fun carregarFilmes() {
        viewModelScope.launch {
            try {
                val listaAtualizada = repository.getAll()
                _uiState.value = _uiState.value.copy(movies = listaAtualizada)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addMovie() {
        val current = _uiState.value
        if (current.inputTitle.isBlank()) return

        val movie = Movie(
            id = 0,
            title = current.inputTitle.trim(),
            genre = current.inputGenre.trim()
        )

        viewModelScope.launch {
            try {
                repository.add(movie)

                _uiState.value = _uiState.value.copy(
                    inputTitle = "",
                    inputGenre = ""
                )
                carregarFilmes()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeMovie(movieId: Long) {
        viewModelScope.launch {
            try {
                repository.remove(movieId)
                carregarFilmes()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}