package com.example

import com.example.config.Config
import com.example.database.DatabaseFactory
import com.example.di.koinModule
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>):Unit =
    EngineMain.main(args)


fun Application.module() {
    Config.init(environment)
    koinModules()
    DatabaseFactory.init()
    configureSerialization()
    session()

    statusPages()
//    configureRouting()
}
