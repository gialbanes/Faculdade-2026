package com.fatec.merge_skills.network

import com.fatec.merge_skills.BASE_URL
import com.fatec.merge_skills.model.Course
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {

    private val httpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = false
            })
        }
    }

    /** GET /courses → Lista todos os cursos */
    suspend fun getCourses(): List<Course> {
        return httpClient.get("$BASE_URL/courses").body()
    }

    /** POST /courses → Cria um novo curso */
    suspend fun createCourse(title: String, description: String?): Course {
        return httpClient.post("$BASE_URL/courses") {
            contentType(ContentType.Application.Json)
            setBody(Course(title = title, description = description))
        }.body()
    }
}