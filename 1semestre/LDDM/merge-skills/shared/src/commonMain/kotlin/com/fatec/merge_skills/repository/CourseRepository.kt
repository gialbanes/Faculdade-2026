package com.fatec.merge_skills.repository

import com.fatec.merge_skills.model.Course

// Interface atua como um contrato funcional
// informo o que pode fazer, e nao como
interface CourseRepository {
    suspend fun getAll(): List<Course>
    suspend fun getById(id: Int): Course?
    suspend fun create(course: Course): Course
    suspend fun update(id: Int, course: Course): Course
    suspend fun delete(id: Int)
}