package com.fatec.ocene.repository

import com.fatec.ocene.model.Product

interface ProductRepository {
    suspend fun getAll(): List<Product>
    suspend fun getByCategoryId(categoryId: Int): List<Product>
    suspend fun getById(id: Int): Product?
    suspend fun create(product: Product): Product
    suspend fun update(id: Int, product: Product): Product
    suspend fun delete(id: Int)
}