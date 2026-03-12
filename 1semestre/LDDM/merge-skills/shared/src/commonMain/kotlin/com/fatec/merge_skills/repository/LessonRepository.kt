package com.fatec.merge_skills.repository

import com.fatec.merge_skills.model.Lesson

interface LessonRepository {
    suspend fun getByCourseId(courseId: Int): List<Lesson>
    suspend fun getById(id: Int): Lesson?
}