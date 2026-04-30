package com.fatec.todolist.model

data class Task (
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val isDone: Boolean = false
)