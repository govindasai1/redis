package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(val text: String)
data class ExceptionMessage(val text: String)