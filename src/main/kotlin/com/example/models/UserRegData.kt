package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDetails(val userName: String, val password: String, val email: String, val phoneNumber: Long)
