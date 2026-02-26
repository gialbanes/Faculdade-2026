package com.fatec.merge_skills.repository

import com.fatec.merge_skills.model.Course

// Interface atua como um contrato funcional
interface CourseRepository {
    // suspect evita o bloqueio da lógica principal
    suspend fun getAll(): List<Course>
    suspend fun getById(id: Int): Course?
    suspend fun create(course: Course): Course
    suspend fun update(id: Int, course: Course): Course
    suspend fun delete(id: Int)
}