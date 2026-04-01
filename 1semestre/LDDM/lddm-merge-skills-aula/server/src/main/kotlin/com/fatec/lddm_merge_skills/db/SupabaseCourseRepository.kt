package com.fatec.lddm_merge_skills.db

import com.fatec.lddm_merge_skills.db.dto.*
import com.fatec.lddm_merge_skills.model.Course
import com.fatec.lddm_merge_skills.repository.CourseRepository
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabaseCourseRepository : CourseRepository {

    private val table = SupabaseFactory.client.postgrest["courses"]

    override suspend fun getAll(): List<Course> = withContext(Dispatchers.IO) {
        table.select().decodeList<Course>()
    }
    override suspend fun getById(id: Int): Course? = withContext(Dispatchers.IO) {
        table.select { filter { eq("id", id) } }.decodeSingleOrNull<Course>()
    }
    override suspend fun create(course: Course): Course = withContext(Dispatchers.IO) {
        table.insert(course.toInsertDTO()) { select() }.decodeSingle<Course>()
    }
    override suspend fun update(id: Int, course: Course): Course = withContext(Dispatchers.IO) {
        table.update(course.toInsertDTO()) { filter { eq("id", id) }; select() }.decodeSingle<Course>()
    }
    override suspend fun delete(id: Int) {
        withContext(Dispatchers.IO) {
            table.delete { filter { eq("id", id) } }
        }
    }


}