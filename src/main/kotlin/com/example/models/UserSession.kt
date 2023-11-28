package com.example.models

import io.ktor.server.auth.*
import java.util.UUID


data class UserSession(val id: UUID):Principal