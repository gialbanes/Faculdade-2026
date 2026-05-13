package com.fatec.lddm_merge_skills.routes

import com.fatec.lddm_merge_skills.repository.LessonRepository
import com.fatec.lddm_merge_skills.repository.QuestionRepository
import io.ktor.http.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.lessonRoutes(
    lessonRepository: LessonRepository,
    questionRepository: QuestionRepository
) {
    get("/lessons/{id}/questions") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
            return@get
        }
        val questions = questionRepository.getByLessonId(id)
        call.respond(questions)
    }

    post("/lessons") {
        try {
            val lessonRequest = call.receive<com.fatec.lddm_merge_skills.model.Lesson>()
            val createdLesson = lessonRepository.create(lessonRequest)
            call.respond(HttpStatusCode.Created, createdLesson)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Formato de licao invalido: verifique course_id"))
        }
    }

    put("/lessons/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@put call.respond(HttpStatusCode.BadRequest, "ID invalido")

        val lessonRequest = call.receive<com.fatec.lddm_merge_skills.model.Lesson>()

        try {
            val updatedLesson = lessonRepository.update(id, lessonRequest)
            call.respond(HttpStatusCode.OK, updatedLesson)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Licao nao encontrada"))
        }
    }

    delete("/lessons/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID invalido")

        try {
            lessonRepository.delete(id)
            call.respond(HttpStatusCode.NoContent)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Licao nao encontrada"))
        }
    }
}