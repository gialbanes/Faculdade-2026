package com.fatec.lddm_merge_skills.db

import com.fatec.lddm_merge_skills.db.dto.*
import com.fatec.lddm_merge_skills.model.Lesson
import com.fatec.lddm_merge_skills.repository.LessonRepository
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabaseLessonRepository : LessonRepository {
    private val table = SupabaseFactory.client.postgrest["lessons"]

    override suspend fun getByCourseId(courseId: Int): List<Lesson> = withContext(Dispatchers.IO) {
        table.select { filter { eq("course_id", courseId) }; order("order", io.github.jan.supabase.postgrest.query.Order.ASCENDING) }.decodeList<Lesson>()
    }
    override suspend fun getById(id: Int): Lesson? = withContext(Dispatchers.IO) {
        table.select { filter { eq("id", id) } }.decodeSingleOrNull<Lesson>()
    }
    override suspend fun create(lesson: Lesson): Lesson = withContext(Dispatchers.IO) {
        table.insert(lesson.toInsertDTO()) { select() }.decodeSingle<Lesson>()
    }
    override suspend fun update(id: Int, lesson: Lesson): Lesson = withContext(Dispatchers.IO) {
        table.update(lesson.toInsertDTO()) { filter { eq("id", id) }; select() }.decodeSingle<Lesson>()
    }
    override suspend fun delete(id: Int) {
        withContext(Dispatchers.IO) {
            table.delete { filter { eq("id", id) } }
        }
    }
}