package com.fatec.merge_skills.db
import com.fatec.merge_skills.model.Lesson
import com.fatec.merge_skills.repository.LessonRepository
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabaseLessonRepository : LessonRepository {
    private val table = SupabaseFactory.client.postgrest["lessons"]

    override suspend fun getByCourseId(courseId: Int): List<Lesson> = withContext(Dispatchers.IO) {
        return@withContext table.select {
            filter { eq("course_id", courseId) }
            order("orderIndex", io.github.jan.supabase.postgrest.query.Order.ASCENDING)
        }.decodeList<Lesson>()
    }

    override suspend fun getById(id: Int): Lesson? = withContext(Dispatchers.IO) {
        return@withContext table.select { filter { eq("id", id) } }.decodeSingleOrNull<Lesson>()
    }
}