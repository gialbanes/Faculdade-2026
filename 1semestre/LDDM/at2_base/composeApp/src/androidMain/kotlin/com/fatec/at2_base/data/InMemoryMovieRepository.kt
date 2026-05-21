package com.fatec.at2_base.data

import com.fatec.at2_base.model.Movie

class InMemoryMovieRepository : MovieRepository {
    private val movies = mutableListOf<Movie>()

    override fun getAll(): List<Movie> = movies.toList()

    override fun add(movie: Movie) {
        movies.add(movie)
    }

    override fun remove(movieId: Long) {
        movies.removeAll { it.id == movieId }
    }
}