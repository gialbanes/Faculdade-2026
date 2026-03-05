package com.fatec.merge_skills.repository

import com.fatec.merge_skills.model.Question

interface QuestionRepository {
    suspend fun getByLessonId(lessonId: Int): List<Question>
    suspend fun getById(id: Int): Question?
}