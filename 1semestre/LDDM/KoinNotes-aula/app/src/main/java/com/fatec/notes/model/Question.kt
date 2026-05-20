package com.fatec.notes.model

data class Question (
    val id: Long = System.currentTimeMillis(),
    val text: String,
    val type: QuestionType,
    val options : List<String>,
    val correctIndex: Int = 0,
    val correctOrder: List<Int> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)