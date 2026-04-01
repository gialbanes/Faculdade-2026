package com.fatec.oceane_atv1

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.fatec.oceane_atv1.db.DatabaseFactory
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) { json() }

    //  Tratamento centralizado de erros
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to (cause.message ?: "Erro interno"))
            )
        }
    }

    DatabaseFactory.init()

    routing {
        get("/") { call.respondText("Serviço Ktor ativo.") }
        get("/health") { call.respondText("OK") }
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
    }
}