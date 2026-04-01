package com.fatec.lddm_merge_skills.repository

import com.fatec.lddm_merge_skills.model.Lesson

interface LessonRepository {
    suspend fun getByCourseId(courseId: Int): List<Lesson>
    suspend fun getById(id: Int): Lesson?
    suspend fun create (lesson: Lesson) : Lesson
    suspend fun update (id: Int, lesson: Lesson) : Lesson
    suspend fun delete (id: Int)
}