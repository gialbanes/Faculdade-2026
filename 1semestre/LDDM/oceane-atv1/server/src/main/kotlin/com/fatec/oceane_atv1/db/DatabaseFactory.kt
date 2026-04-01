package com.fatec.oceane_atv1.db

import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init() {
        // Se não houver variável de ambiente, usa os valores EXATOS do seu docker-compose
        val dbUrl = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/oceane_db"
        val dbUser = System.getenv("DB_USER") ?: "devuser" // Corrigido de devpassword para devuser
        val dbPassword = System.getenv("DB_PASSWORD") ?: "devpassword"

        println("Conectando ao banco: $dbUrl com o usuário: $dbUser")

        try {
            val flyway = Flyway.configure()
                .dataSource(dbUrl, dbUser, dbPassword)
                .locations("classpath:com/fatec/oceane_atv1/db/migration")
                .baselineOnMigrate(true)
                .load()

            flyway.migrate()

            Database.connect(
                url = dbUrl,
                driver = "org.postgresql.Driver",
                user = dbUser,
                password = dbPassword
            )
        } catch (e: Exception) {
            println("Erro na conexão: ${e.message}")
            throw e
        }
    }
}