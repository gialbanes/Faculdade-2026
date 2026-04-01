package com.fatec.lddm_merge_skills.repository

import com.fatec.lddm_merge_skills.model.Question

interface QuestionRepository {
    suspend fun getByLessonId(lessonId: Int): List<Question>
    suspend fun getById(id: Int): Question?
    suspend fun create (question: Question) : Question
    suspend fun update (id: Int, question: Question) : Question
    suspend fun delete (id: Int)
}