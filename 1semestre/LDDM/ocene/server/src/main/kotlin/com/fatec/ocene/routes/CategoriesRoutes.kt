package com.fatec.ocene.routes

import com.fatec.ocene.model.Category
import com.fatec.ocene.repository.CategoryRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoryRoutes(repository: CategoryRepository) {

    get("/categories") {
        val categories = repository.getAll()
        call.respond(categories)
    }

    get("/categories/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
            return@get
        }

        val category = repository.getById(id)
            ?: return@get call.respond(HttpStatusCode.NotFound, mapOf("error" to "Categoria não encontrada"))

        call.respond(category)
    }

    post("/categories") {
        try {
            val categoryRequest = call.receive<Category>()
            val createdCategory = repository.create(categoryRequest)
            call.respond(HttpStatusCode.Created, createdCategory)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Formato de categoria inválido"))
        }
    }

    put("/categories/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@put call.respond(HttpStatusCode.BadRequest, "ID ausente")

        val categoryRequest = call.receive<Category>()

        try {
            val updatedCategory = repository.update(id, categoryRequest)
            call.respond(HttpStatusCode.OK, updatedCategory)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Categoria não encontrada"))
        }
    }

    delete("/categories/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID ausente ou inválido")

        try {
            repository.delete(id)
            call.respond(HttpStatusCode.NoContent)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Categoria não encontrada"))
        }
    }
}