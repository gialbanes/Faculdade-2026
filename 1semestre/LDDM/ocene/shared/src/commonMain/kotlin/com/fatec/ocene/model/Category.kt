package com.fatec.ocene.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int = 0,
    val name: String,
    val description: String? = null,
)