package com.fatec.notes.model

// modelo de dados de uma nota
data class Note (
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val content: String = "",
    val createdAt: Long = System.currentTimeMillis()
)