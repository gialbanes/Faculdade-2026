package com.fatec.at2_base.model

data class Movie (
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val genre: String = "",
    val createdAt: Long = System.currentTimeMillis()
)