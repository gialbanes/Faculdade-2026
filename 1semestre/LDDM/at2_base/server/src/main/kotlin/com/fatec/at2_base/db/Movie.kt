package com.fatec.at2_base.db

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Long,
    val title: String,
    val genre: String
)

object MovieDatabase {
    val movies = mutableListOf<Movie>(
        Movie(1, "Interestelar", "Ficção Científica"),
        Movie(2, "O Fabuloso Destino de Amélie Poulain", "Comédia / Drama")
    )
}