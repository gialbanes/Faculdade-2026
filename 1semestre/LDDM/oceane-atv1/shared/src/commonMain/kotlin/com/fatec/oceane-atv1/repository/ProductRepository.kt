package com.fatec.oceane_atv1.repository

import com.fatec.oceane_atv1.model.Product

interface ProductRepository {
    suspend fun getByCategoryId(categoryId: Int): List<Product>
    suspend fun getById(id: Int): Product?
    suspend fun create(product: Product): Product
    suspend fun update(id: Int, product: Product): Product
    suspend fun delete(id: Int)
}