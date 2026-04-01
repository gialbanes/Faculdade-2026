package com.fatec.oceane_atv1.repository

import com.fatec.oceane_atv1.model.Category

// Interface atua como um contrato funcional
// informo o que pode fazer, e nao como
interface CategoryRepository {
    suspend fun getAll(): List<Category>
    suspend fun getById(id: Int): Category?
    suspend fun create(category: Category): Category
    suspend fun update(id: Int, category: Category): Category
    suspend fun delete(id: Int)
}