package com.fatec.merge_skills.routes

import com.fatec.merge_skills.repository.CourseRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.response.respond

fun Route.courseRoutes(
    courseRepository: CourseRepository,
    lessonRepository: LessonRepository
) {
    // GET /courses = lista de todos os cursos
    get("/courses"){
        val courses = courseRepository.getAll()
        call.respond(courses)
    }

    // GET /courses/{id}/lessons = lições de um curso
    get("/courses/{id}/lessons"){
        val id = call.parameters["id"]?.toIntOrNull()
        if(id == null){
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválid")) // mapTo para passar a mensagem
            return@get
        }
        val lessons = lessonRepository.getByCourseId(id)
        call.respond(lessons)
    }
}