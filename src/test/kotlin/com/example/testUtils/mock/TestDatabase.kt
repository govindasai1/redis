package com.example.testUtils.mock

import com.example.tables.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object TestDatabase {
    private var database: Database? = null
    fun init() {

        val config = HikariConfig()
        config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;INIT=create domain if not exists jsonb as other;"
        config.maximumPoolSize = 2
        config.isAutoCommit = true
        config.username = "postgres"
        config.password = "root"
        config.validate()
        val source = HikariDataSource(config)

        database = Database.connect(source)

        transaction(database){
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(UserTable)
        }
    }

    fun destroyTestDB() {
        transaction {
            SchemaUtils.dropDatabase()
        }
    }
}