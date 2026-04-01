package com.fatec.ocene.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int = 0,
    @SerialName("category_id")
    val categoryId: Int,
    val name: String,
    val description: String? = null,
    val price: Double,
    val quantity: Int
)