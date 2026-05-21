package com.fatec.at2_base.routes

import com.fatec.at2_base.db.Movie
import com.fatec.at2_base.db.MovieDatabase
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.movieRoutes() {

    get("/movies") {
        call.respond(MovieDatabase.movies)
    }

    post("/movies") {
        try {
            val movieRequest = call.receive<Movie>()

            val newMovie = movieRequest.copy(id = System.currentTimeMillis())

            MovieDatabase.movies.add(newMovie)

            call.respond(HttpStatusCode.Created, newMovie)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Formato de filme inválido"))
        }
    }

    delete("/movies/{id}") {
        val id = call.parameters["id"]?.toLongOrNull()
            ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID ausente ou inválido")

        val removed = MovieDatabase.movies.removeIf { it.id == id }

        if (removed) {
            call.respond(HttpStatusCode.NoContent)
        } else {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Filme não encontrado"))
        }
    }
}