package com.fatec.oceane_atv1.db.migration

import com.fatec.oceane_atv1.db.Categories
import com.fatec.oceane_atv1.db.Products
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class V2__Seed_Data : BaseJavaMigration() {

    override fun migrate(context: Context?) {
        val connection = context?.connection ?: return
        val database = Database.connect({ FlywayConnection(connection) })

        transaction(database) {
            seedCategoriesAndProducts()
        }
    }

    private fun seedCategoriesAndProducts() {
        // 1. Definição dos dados de Categoria
        data class CategorySeed(val name: String, val description: String)

        val categories = listOf(
            CategorySeed("Maquiagem", "Produtos para rosto, olhos e lábios"),
            CategorySeed("Skincare", "Cuidados com a pele, hidratação e limpeza"),
            CategorySeed("Acessórios", "Pincéis, esponjas e organizadores"),
            CategorySeed("Cabelos", "Tratamento, finalização e escovas")
        )

        // 2. Inserção das Categorias e mapeamento de IDs para os Produtos
        categories.forEach { cat ->
            val catId = Categories.insert {
                it[name] = cat.name
                it[description] = cat.description
            } get Categories.id

            // 3. Inserção de Produtos específicos para cada categoria criada
            when (cat.name) {
                "Maquiagem" -> {
                    insertProduct(catId.value, "Paleta de Sombras Edition", 89.90, 50, "24 cores vibrantes")
                    insertProduct(catId.value, "Batom Matte Real", 35.00, 100, "Longa duração e alta pigmentação")
                }
                "Skincare" -> {
                    insertProduct(catId.value, "Sérum Ácido Hialurônico", 65.00, 30, "Hidratação profunda")
                    insertProduct(catId.value, "Gel de Limpeza Facial", 42.90, 45, "Limpeza suave sem ressecar")
                }
                "Acessórios" -> {
                    insertProduct(catId.value, "Esponja de Maquiagem", 22.00, 200, "Acabamento natural")
                    insertProduct(catId.value, "Kit de Pincéis Profissionais", 150.00, 15, "12 pincéis essenciais")
                }
                "Cabelos" -> {
                    insertProduct(catId.value, "Óleo Reparador", 55.00, 40, "Brilho intenso e antifrizz")
                }
            }
        }
    }

    private fun insertProduct(catId: Int, name: String, price: Double, qty: Int, desc: String) {
        Products.insert {
            it[categoryId] = catId
            it[this.name] = name
            it[this.price] = price
            it[this.quantity] = qty
            it[this.description] = desc
        }
    }
}