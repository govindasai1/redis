package com.example.database

import com.example.config.Config
import com.example.tables.UserTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val url = Config.env.url
        val driver = Config.env.driver
        val userName = Config.env.user
        val password = Config.env.pass

        Database.connect(url, driver, userName, password)

        transaction {
            SchemaUtils.createMissingTablesAndColumns(UserTable)
        }
    }

    suspend fun <T> dbQuery(block: () -> T): T = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }

}