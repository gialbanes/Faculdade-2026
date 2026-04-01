package com.fatec.lddm_merge_skills.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// @Serializable: habilita a conversão da classe para/de JSON
@Serializable
data class Course(
    val id: Int = 0,
    val title: String,
    val description: String? = null,    // Null Safety: permite valores nulos
    val icon: String? = null,
    val color: String? = null,
    @SerialName("total_lessons")        // Mapeamento automático camelCase → snake_case
    val totalLessons: Int? = null
)