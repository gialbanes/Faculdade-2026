package com.fatec.ocene.routes

import com.fatec.ocene.model.Product
import com.fatec.ocene.repository.ProductRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive

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

        val currentProduct = repository.getById(id)
            ?: return@put call.respond(HttpStatusCode.NotFound, mapOf("error" to "Produto não encontrado"))

        val body = try {
            call.receive<JsonObject>()
        } catch (e: Exception) {
            return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Formato de produto inválido"))
        }

        val newCategoryId = if (body.containsKey("category_id")) {
            body["category_id"]?.jsonPrimitive?.intOrNull
                ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "category_id inválido"))
        } else {
            currentProduct.categoryId
        }

        val newPrice = if (body.containsKey("price")) {
            body["price"]?.jsonPrimitive?.doubleOrNull
                ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "price inválido"))
        } else {
            currentProduct.price
        }

        val newQuantity = if (body.containsKey("quantity")) {
            body["quantity"]?.jsonPrimitive?.intOrNull
                ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "quantity inválido"))
        } else {
            currentProduct.quantity
        }

        val newName = body["name"]?.jsonPrimitive?.contentOrNull ?: currentProduct.name
        val newDescription = if (body.containsKey("description")) {
            body["description"]?.jsonPrimitive?.contentOrNull
        } else {
            currentProduct.description
        }

        val updatedProduct = currentProduct.copy(
            categoryId = newCategoryId,
            name = newName,
            description = newDescription,
            price = newPrice,
            quantity = newQuantity,
        )

        val savedProduct = repository.update(id, updatedProduct)
        call.respond(HttpStatusCode.OK, savedProduct)
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