package com.fatec.merge_skills.db
import com.fatec.merge_skills.model.Question
import com.fatec.merge_skills.repository.QuestionRepository
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabaseQuestionRepository : QuestionRepository {
    private val table = SupabaseFactory.client.postgrest["questions"]

    override suspend fun getByLessonId(lessonId: Int): List<Question> = withContext(Dispatchers.IO) {
        return@withContext table.select { filter { eq("lesson_id", lessonId) } }.decodeList<Question>()
    }

    override suspend fun getById(id: Int): Question? = withContext(Dispatchers.IO) {
        return@withContext table.select { filter { eq("id", id) } }.decodeSingleOrNull<Question>()
    }
}