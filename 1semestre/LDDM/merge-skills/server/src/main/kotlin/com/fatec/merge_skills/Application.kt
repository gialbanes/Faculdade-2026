package com.fatec.merge_skills

import com.fatec.merge_skills.db.DatabaseFactory
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.fatec.merge_skills.routes.courseRoutes
import com.fatec.merge_skills.routes.lessonRoutes
import com.fatec.merge_skills.routes.questionRoutes
import com.fatec.merge_skills.routes.progressRoutes
import com.fatec.lddm_merge_skills.db.ExposedCourseRepository
import com.fatec.merge_skills.db.ExposedLessonRepository
import com.fatec.merge_skills.db.ExposedQuestionRepository

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    DatabaseFactory.init() // Delegação do banco inserida na inicialização do Ktor
    val courseRepository = ExposedCourseRepository()
    val lessonRepository = ExposedLessonRepository()
    val questionRepository = ExposedQuestionRepository()

    routing {
        get("/") { call.respondText("Serviço Ktor ativo.") }
        get("/health") { call.respondText("OK") }

        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")

        //  Semana 03: Rotas da API
        courseRoutes(courseRepository, lessonRepository)
        lessonRoutes(questionRepository)
        questionRoutes(questionRepository)
        progressRoutes(questionRepository)
    }
}