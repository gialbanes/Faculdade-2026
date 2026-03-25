package com.fatec.lddm_oceane.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.awt.SystemColor.text
import com.fatec.lddm_oceane.model.Category

class Tables {
    object Products : IntIdTable("products") {
        val name = text("name")
        val description = text("description")
        val price = double("price")
        val categoryId = reference("category_id", Category, onDelete = ReferenceOption.CASCADE)
    }

    object Categoryies : IntIdTable("categories") {
        val name = text("name")
        val description = text("description")
    }
}