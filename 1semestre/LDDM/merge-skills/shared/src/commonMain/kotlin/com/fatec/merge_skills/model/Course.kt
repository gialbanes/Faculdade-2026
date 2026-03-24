package com.fatec.merge_skills.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// @Serializable: habilita a conversão da classe para/de JSON
@Serializable
data class Course(
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val icon: String? = null,
    val color: String? = null,
    @SerialName("total_lessons") val totalLessons: Int? = null
)