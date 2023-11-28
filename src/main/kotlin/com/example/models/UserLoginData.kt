package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginDetails(val userName: String, val password: String)