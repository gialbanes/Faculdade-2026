package com.fatec.at2_base.data

import com.fatec.at2_base.model.Movie

interface MovieRepository {
    fun getAll() : List<Movie>
    fun add(movie: Movie)
    fun remove(movieId: Long)
}