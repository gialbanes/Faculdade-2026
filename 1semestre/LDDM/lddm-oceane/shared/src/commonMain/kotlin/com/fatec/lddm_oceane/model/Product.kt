package com.fatec.lddm_oceane.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    @SerialName("category_id")
    val categoryId: Int,
    val quantity: Int

)
