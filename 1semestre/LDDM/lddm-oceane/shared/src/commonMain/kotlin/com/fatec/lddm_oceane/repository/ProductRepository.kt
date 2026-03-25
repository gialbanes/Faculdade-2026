package com.fatec.lddm_oceane.repository

import com.fatec.lddm_oceane.model.Product

interface ProductRepository {
    suspend fun getAll(): List<Product>
    suspend fun getById(id: Int): Product?
    suspend fun create(product: Product): Product
    suspend fun update(id: Int, product: Product): Product
    suspend fun delete(id: Int)
}