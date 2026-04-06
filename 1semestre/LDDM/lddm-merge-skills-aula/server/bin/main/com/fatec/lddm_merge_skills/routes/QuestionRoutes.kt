package com.fatec.lddm_merge_skills.routes

import com.fatec.lddm_merge_skills.repository.QuestionRepository
import io.ktor.http.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.questionRoutes(questionRepository: QuestionRepository) {
    get("/questions/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
            return@get
        }
        val question = questionRepository.getById(id)
        if (question == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Questão não encontrada"))
            return@get
        }
        call.respond(question)
    }

    post("/questions") {
        try {
            val questionRequest = call.receive<com.fatec.lddm_merge_skills.model.Question>()
            val createdQuestion = questionRepository.create(questionRequest)
            call.respond(HttpStatusCode.Created, createdQuestion)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Formato de questao invalido"))
        }
    }

    put("/questions/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@put call.respond(HttpStatusCode.BadRequest, "ID invalido")

        val questionRequest = call.receive<com.fatec.lddm_merge_skills.model.Question>()

        try {
            val updatedQuestion = questionRepository.update(id, questionRequest)
            call.respond(HttpStatusCode.OK, updatedQuestion)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Questao nao encontrada"))
        }
    }

    delete("/questions/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID invalido")

        try {
            questionRepository.delete(id)
            call.respond(HttpStatusCode.NoContent)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Questao nao encontrada"))
        }
    }
}