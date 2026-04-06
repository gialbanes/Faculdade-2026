package com.fatec.ocene.db

import com.fatec.ocene.model.Category
import com.fatec.ocene.repository.CategoryRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedCategoryRepository : CategoryRepository {
    private fun ResultRow.toCategory() = Category(
        id = this[Categories.id].value,
        name = this[Categories.name],
        description = this[Categories.description]
    )

    override suspend fun getAll(): List<Category> = newSuspendedTransaction {
        Categories.selectAll().map { it.toCategory() }
    }

    override suspend fun getById(id: Int): Category? = newSuspendedTransaction {
        Categories.selectAll()
            .where { Categories.id eq id }
            .map { it.toCategory() }
            .singleOrNull()
    }

    override suspend fun create(category: Category): Category = newSuspendedTransaction {
        val insertStatement = Categories.insert {
            it[name] = category.name
            it[description] = category.description
        }
        insertStatement.resultedValues!!.first().toCategory()
    }

    override suspend fun update(id: Int, category: Category): Category = newSuspendedTransaction {
        Categories.update({ Categories.id eq id }) {
            it[name] = category.name
            it[description] = category.description
        }

        Categories.selectAll()
            .where { Categories.id eq id }
            .map { it.toCategory() }
            .single()
    }

    override suspend fun delete(id: Int): Unit = newSuspendedTransaction {
        Categories.deleteWhere { Categories.id eq id }
    }
}