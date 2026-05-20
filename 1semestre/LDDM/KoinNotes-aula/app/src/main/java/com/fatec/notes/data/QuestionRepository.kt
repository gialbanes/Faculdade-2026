package com.fatec.notes.data

import com.fatec.notes.model.Question

interface QuestionRepository {
    fun getAll(): List<Question>
    fun add(question: Question)
    fun remove(questionId: Long)
}