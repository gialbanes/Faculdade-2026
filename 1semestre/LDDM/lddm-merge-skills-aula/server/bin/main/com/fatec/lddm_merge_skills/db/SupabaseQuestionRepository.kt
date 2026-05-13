package com.fatec.lddm_merge_skills.db

import com.fatec.lddm_merge_skills.db.dto.*
import com.fatec.lddm_merge_skills.model.Question
import com.fatec.lddm_merge_skills.repository.QuestionRepository
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabaseQuestionRepository : QuestionRepository {
    private val table = SupabaseFactory.client.postgrest["questions"]

    override suspend fun getByLessonId(lessonId: Int): List<Question> = withContext(Dispatchers.IO) {
        table.select { filter { eq("lesson_id", lessonId) }; order("order", io.github.jan.supabase.postgrest.query.Order.ASCENDING) }.decodeList<Question>()
    }
    override suspend fun getById(id: Int): Question? = withContext(Dispatchers.IO) {
        table.select { filter { eq("id", id) } }.decodeSingleOrNull<Question>()
    }
    override suspend fun create(question: Question): Question = withContext(Dispatchers.IO) {
        table.insert(question.toInsertDTO()) { select() }.decodeSingle<Question>()
    }
    override suspend fun update(id: Int, question: Question): Question = withContext(Dispatchers.IO) {
        table.update(question.toInsertDTO()) { filter { eq("id", id) }; select() }.decodeSingle<Question>()
    }
    override suspend fun delete(id: Int) {
        withContext(Dispatchers.IO) {
            table.delete { filter { eq("id", id) } }
        }
    }
}