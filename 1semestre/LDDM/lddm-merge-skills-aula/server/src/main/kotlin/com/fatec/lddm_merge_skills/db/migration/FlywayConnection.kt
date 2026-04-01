package com.fatec.lddm_merge_skills.db.migration

import java.sql.Connection

/**
 * Wrapper de conexão técnica (impede travamentos entre Flyway e Exposed)
 */
class FlywayConnection(private val delegate: Connection) : Connection by delegate {
    override fun close() {}
    override fun setTransactionIsolation(level: Int) {}
    override fun setAutoCommit(autoCommit: Boolean) {}
    override fun setReadOnly(readOnly: Boolean) {}
}