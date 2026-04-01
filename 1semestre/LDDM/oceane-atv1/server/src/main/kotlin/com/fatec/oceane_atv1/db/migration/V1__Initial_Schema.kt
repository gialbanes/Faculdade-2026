package com.fatec.oceane_atv1.db.migration

import com.fatec.oceane_atv1.db.Categories
import com.fatec.oceane_atv1.db.Products
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class V1__Initial_Schema : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        val connection = context?.connection ?: throw RuntimeException("Conexão do Flyway está nula")

        // Liga o Exposed à conexão que o Flyway abriu
        val database = Database.connect({ FlywayConnection(connection) })

        transaction(database) {
            SchemaUtils.create(Categories, Products)
        }
    }
}