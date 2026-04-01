package com.fatec.ocene.repository

import com.fatec.ocene.model.Category

interface CategoryRepository {
    suspend fun getAll(): List<Category>
    suspend fun getById(id: Int): Category?
    suspend fun create(category: Category): Category
    suspend fun update(id: Int, category: Category): Category
    suspend fun delete(id: Int)
}