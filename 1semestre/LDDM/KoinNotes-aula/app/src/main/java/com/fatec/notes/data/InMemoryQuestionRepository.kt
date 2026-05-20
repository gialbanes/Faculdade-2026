package com.fatec.notes.data

import com.fatec.notes.model.Question

class InMemoryQuestionRepository : QuestionRepository {
    private val questions = mutableListOf<Question>()

    override fun getAll(): List<Question> = questions.toList()

    override fun add(question: Question) {
        questions.add(question)
    }

    override fun remove(questionId: Long) {
        questions.removeAll { it.id == questionId }
    }
}