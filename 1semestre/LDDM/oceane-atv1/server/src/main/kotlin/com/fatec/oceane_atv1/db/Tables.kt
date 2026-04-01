package com.fatec.oceane_atv1.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Categories : IntIdTable("categories") {
    val name = text("name")
    val description = text("description").nullable()
}

object Products : IntIdTable("products") {
    val categoryId = reference("category_id", Categories, onDelete = ReferenceOption.CASCADE)
    val name = text("name")
    val description = text("description").nullable()
    val price = double("price")
    val quantity = integer("quantity")
}

// Tabela de Reviews (Sugerida para completar os 3 modelos)
object Reviews : IntIdTable("reviews") {
    val productId = reference("product_id", Products, onDelete = ReferenceOption.CASCADE)
    val userName = text("user_name")
    val rating = integer("rating") // Ex: 1 a 5
    val comment = text("comment").nullable()
    val createdAt = text("created_at") // Você pode usar datetime se configurar o modulo JavaTime do Exposed
}