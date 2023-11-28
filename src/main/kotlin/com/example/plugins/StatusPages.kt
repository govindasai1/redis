package com.example.plugins

import com.example.exception.CommonException
import com.example.exception.NoUserException
import com.example.models.ExceptionMessage
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.statusPages() {
    install(StatusPages) {
        exception<CommonException> { call, exception ->
            call.respond(ExceptionMessage(exception.text))
        }
        exception<NoUserException>{call, noUserException ->
            call.respond(ExceptionMessage(noUserException.text))
        }
        exception< ApplicationConfigurationException>{call, applicationConfigurationException ->
            call.respond(ExceptionMessage(applicationConfigurationException.message?:"url or driver or pass or user in getting null"))
        }
    }
}