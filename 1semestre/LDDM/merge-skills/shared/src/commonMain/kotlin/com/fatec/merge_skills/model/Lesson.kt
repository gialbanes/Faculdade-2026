package com.fatec.merge_skills.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    val id: Int = 0,
    @SerialName("course_id") val courseId: Int,
    val title: String,
    val description: String? = null,
    val order: Int? = null
)