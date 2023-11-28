package com.example.plugins

import com.example.models.UserSession
import com.example.redis.RedisSessionStorage
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import org.koin.ktor.ext.inject

fun Application.session() {
    val redisSessions by inject<RedisSessionStorage>()
    install(Sessions) {
        val secretHexEncryptKey = hex("00112233445566778899aabbccddeeff")
        val secretHexSignKey = hex("6819b57a326945c1968f45236589")

        cookie<UserSession>("userSession", redisSessions) {
            transform(SessionTransportTransformerEncrypt(secretHexEncryptKey, secretHexSignKey))
        }
    }
//    install(Authentication){
//        session<UserSession>("SESSION_AUTH") {
//            validate { session ->
//                if(session.id.toString().isNotEmpty())
//                    session
//                else
//                    null
//            }
////            challenge{
////                throw InvalidTokenException(
////                    "INVALID TOKEN",
////                    HttpStatusCode.Unauthorized
////                )
////            }
//        }
//    }
}