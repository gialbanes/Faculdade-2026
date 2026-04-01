package com.fatec.ocene

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.fatec.ocene.db.DatabaseFactory
import com.fatec.ocene.db.ExposedProductRepository
import com.fatec.ocene.db.ExposedCategoryRepository
import com.fatec.ocene.routes.categoryRoutes
import com.fatec.ocene.routes.productRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import com.fatec.ocene.Greeting
import com.fatec.ocene.SERVER_PORT

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) { json() }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to (cause.message ?: "Erro interno"))
            )
        }
    }

    DatabaseFactory.init()

    val productRepository = ExposedProductRepository()
    val categoryRepository = ExposedCategoryRepository()

    routing {
        get("/") { call.respondText("Ktor: ${Greeting().greet()}") }
        get("/health") { call.respondText("OK") }

        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")

        productRoutes(productRepository)
        categoryRoutes(categoryRepository)
    }
}
