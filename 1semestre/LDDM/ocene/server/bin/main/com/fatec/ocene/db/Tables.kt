package com.fatec.ocene.db

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
