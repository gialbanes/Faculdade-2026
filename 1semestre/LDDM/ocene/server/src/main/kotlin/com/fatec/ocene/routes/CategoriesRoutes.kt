package com.fatec.ocene.routes

import com.fatec.ocene.model.Category
import com.fatec.ocene.repository.CategoryRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

fun Route.categoryRoutes(repository: CategoryRepository) {

    get("/categories") {
        val categories = repository.getAll()
        call.respond(categories)
    }

    get("/categories/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))

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

        val currentCategory = repository.getById(id)
            ?: return@put call.respond(HttpStatusCode.NotFound, mapOf("error" to "Categoria não encontrada"))

        val body = try {
            call.receive<JsonObject>()
        } catch (e: Exception) {
            return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Formato de categoria inválido"))
        }

        val newName = body["name"]?.jsonPrimitive?.contentOrNull ?: currentCategory.name
        val newDescription = if (body.containsKey("description")) {
            body["description"]?.jsonPrimitive?.contentOrNull
        } else {
            currentCategory.description
        }

        val updatedCategory = currentCategory.copy(
            name = newName,
            description = newDescription,
        )

        val savedCategory = repository.update(id, updatedCategory)
        call.respond(HttpStatusCode.OK, savedCategory)
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