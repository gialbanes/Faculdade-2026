package com.fatec.lddm_merge_skills.routes

import com.fatec.lddm_merge_skills.repository.CourseRepository
import com.fatec.lddm_merge_skills.repository.LessonRepository
import io.ktor.http.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.courseRoutes(
    courseRepository: CourseRepository,
    lessonRepository: LessonRepository
) {
    // GET /courses → Lista todos os cursos
    get("/courses") {
        val courses = courseRepository.getAll()
        call.respond(courses)
    }

    // GET /courses/{id}/lessons → Lições de um curso
    get("/courses/{id}/lessons") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
            return@get
        }
        val lessons = lessonRepository.getByCourseId(id)
        call.respond(lessons)
    }

    post("/courses") {
        try {
            val courseRequest = call.receive<com.fatec.lddm_merge_skills.model.Course>()
            val createdCourse = courseRepository.create(courseRequest)
            call.respond(HttpStatusCode.Created, createdCourse)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Formato de curso invalido"))
        }
    }

    put("/courses/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@put call.respond(HttpStatusCode.BadRequest, "ID ausente")

        val courseRequest = call.receive<com.fatec.lddm_merge_skills.model.Course>()

        try {
            val updatedCourse = courseRepository.update(id, courseRequest)
            call.respond(HttpStatusCode.OK, updatedCourse)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Curso nao encontrado"))
        }
    }

    delete("/courses/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID ausente ou invalido")

        try {
            courseRepository.delete(id)
            call.respond(HttpStatusCode.NoContent)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Curso nao encontrado"))
        }
    }
}