package com.fatec.oceane_atv1.routes

import com.fatec.oceane_atv1.model.Product
import com.fatec.oceane_atv1.repository.ProductRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRoutes(repository: ProductRepository) {

    route("/products") {
        // GET - Listar todos
        get {
            val products = repository.getAll()
            call.respond(products)
        }

        // POST - Criar novo
        post {
            val product = call.receive<Product>()
            val created = repository.create(product)
            call.respond(HttpStatusCode.Created, created)
        }

        route("/{id}") {
            // GET by ID
            get {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                val product = repository.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respond(product)
            }

            // PUT - Atualizar
            put {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
                val product = call.receive<Product>()
                val updated = repository.update(id, product)
                if (updated) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
            }

            // DELETE - Remover
            delete {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
                val deleted = repository.delete(id)
                if (deleted) call.respond(HttpStatusCode.NoContent) else call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}