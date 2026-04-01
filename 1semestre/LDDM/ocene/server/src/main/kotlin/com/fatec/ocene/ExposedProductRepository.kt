package com.fatec.ocene.db

import com.fatec.ocene.model.Product
import com.fatec.ocene.repository.ProductRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedProductRepository : ProductRepository {

    private fun ResultRow.toProduct() = Product(
        id = this[Products.id].value,
        categoryId = this[Products.categoryId].value,
        name = this[Products.name],
        description = this[Products.description],
        price = this[Products.price],
        quantity = this[Products.quantity]
    )

    override suspend fun getAll(): List<Product> = newSuspendedTransaction {
        Products.selectAll().map { it.toProduct() }
    }

    override suspend fun getByCategoryId(categoryId: Int): List<Product> = newSuspendedTransaction {
        Products.selectAll()
            .where { Products.categoryId eq categoryId }
            .map { it.toProduct() }
    }

    override suspend fun getById(id: Int): Product? = newSuspendedTransaction {
        Products.selectAll()
            .where { Products.id eq id }
            .map { it.toProduct() }
            .singleOrNull()
    }

    override suspend fun create(product: Product): Product = newSuspendedTransaction {
        val insertStatement = Products.insert {
            it[categoryId] = product.categoryId // O Exposed aceita o Int aqui
            it[name] = product.name
            it[description] = product.description
            it[price] = product.price
            it[quantity] = product.quantity
        }
        insertStatement.resultedValues!!.first().toProduct()
    }

    override suspend fun update(id: Int, product: Product): Product = newSuspendedTransaction {
        Products.update({ Products.id eq id }) {
            it[categoryId] = product.categoryId
            it[name] = product.name
            it[description] = product.description
            it[price] = product.price
            it[quantity] = product.quantity
        }

        Products.selectAll()
            .where { Products.id eq id }
            .map { it.toProduct() }
            .single()
    }

    override suspend fun delete(id: Int): Unit = newSuspendedTransaction {
        Products.deleteWhere { Products.id eq id }
    }
}