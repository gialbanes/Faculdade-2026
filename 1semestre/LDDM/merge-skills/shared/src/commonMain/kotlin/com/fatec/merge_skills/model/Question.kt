package com.fatec.merge_skills.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Int = 0,
    @SerialName("lesson_id") val lessonId: Int,
    val question: String,
    val code: String? = null,
    val options: List<String> = emptyList(),
    @SerialName("correct_answer") val correctAnswer: Int? = null,
    val order: Int? = null
)