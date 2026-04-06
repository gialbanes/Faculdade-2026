package com.fatec.ocene.routes

import com.fatec.ocene.model.Product
import com.fatec.ocene.repository.ProductRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRoutes(repository: ProductRepository) {

    get("/products") {
        val products = repository.getAll()
        call.respond(products)
    }

    get("/products/category/{categoryId}") {
        val categoryId = call.parameters["categoryId"]?.toIntOrNull()
            ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID da categoria inválido"))

        val products = repository.getByCategoryId(categoryId)
        call.respond(products)
    }

    get("/products/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))

        val product = repository.getById(id)
            ?: return@get call.respond(HttpStatusCode.NotFound, mapOf("error" to "Produto não encontrado"))

        call.respond(product)
    }

    post("/products") {
        try {
            val productRequest = call.receive<Product>()
            val createdProduct = repository.create(productRequest)
            call.respond(HttpStatusCode.Created, createdProduct)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Formato de produto inválido"))
        }
    }

    put("/products/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@put call.respond(HttpStatusCode.BadRequest, "ID ausente")

        val productRequest = call.receive<Product>()

        try {
            val updatedProduct = repository.update(id, productRequest)
            call.respond(HttpStatusCode.OK, updatedProduct)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Produto não encontrado"))
        }
    }

    delete("/products/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID ausente ou inválido")

        try {
            repository.delete(id)
            call.respond(HttpStatusCode.NoContent)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Produto não encontrado"))
        }
    }
}