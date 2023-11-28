package com.example.route

import com.example.models.Message
import com.example.models.RegisterDetails
import com.example.models.UserLoginDetails
import com.example.models.UserSession
import com.example.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.koin.ktor.ext.inject

fun Route.userRoute() {
    val userServiceObject by inject<UserService>()
    route("/User") {

        post("/Register") {
            val response = call.receive<RegisterDetails>()
            call.respond(HttpStatusCode.Created, userServiceObject.registerUser(response))
        }

        post("/Login") {
            val response = call.receive<UserLoginDetails>()
            val result = userServiceObject.loginUser(response)
            if (result != null) {
                call.sessions.set(UserSession(result))
                call.respond(Message("Logged in Successfully"))
            } else call.respond(Message("UserName or Password is Incorrect"))
        }


            get("/getData") {
                val id = call.sessions.get<UserSession>()?.id
                if (id == null)
                    call.respond(Message("To $id get Data Login First"))
                else
                    call.respond(userServiceObject.userDetails(id))
            }

        delete("/DeleteUser"){
            val id = call.sessions.get<UserSession>()
            if (id == null) call.respond(Message("To Delete User Login First"))
            else call.respond(userServiceObject.deleteUser(id.id))
        }
    }
}