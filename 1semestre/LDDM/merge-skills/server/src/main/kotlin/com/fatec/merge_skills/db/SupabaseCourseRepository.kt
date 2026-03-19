package com.fatec.merge_skills.db

import com.fatec.merge_skills.model.Course
import com.fatec.merge_skills.repository.CourseRepository
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabaseCourseRepository : CourseRepository {
    private val table = SupabaseFactory.client.postgrest["courses"]

    // Dispatchers.IO: pra nao travar a aplicação, de forma assíncrona
    override suspend fun getAll(): List<Course> = withContext(Dispatchers.IO) {
        return@withContext table.select().decodeList<Course>()
    }

    override suspend fun getById(id: Int): Course? = withContext(Dispatchers.IO) {
        return@withContext table.select { filter { eq("id", id)}}.decodeSingleOrNull<Course>()
    }

    override suspend fun create(course: Course): Course = withContext(Dispatchers.IO) {
        return@withContext table.insert(course) { select()}.decodeSingle<Course>()
    }

    override suspend fun update(id: Int, course: Course): Course = withContext(Dispatchers.IO) {
        return@withContext table.update({
            set("title", course.title)
            set("description", course.description)
            if (course.icon != null) set("icon", course.icon)
            if (course.color != null) set("color", course.color)
            if (course.totalLessons != null) set("total_lessons", course.totalLessons)
        }){ filter {eq("id", id)}; select() }.decodeSingle<Course>()
    }

    override suspend fun delete(id: Int): Unit = withContext(Dispatchers.IO) {
        table.delete { filter { eq("id", id)}}
    }
}