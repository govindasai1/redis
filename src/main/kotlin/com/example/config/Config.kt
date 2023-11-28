package com.example.config

import io.ktor.server.application.*
import io.ktor.server.config.*

object Config {
    lateinit var env:ConfigParameters
    fun init(environment:ApplicationEnvironment){
        env = ConfigParameters(
            url = environment.config.validateAndGetString("storage.url"),
     driver =  environment.config.validateAndGetString("storage.driver"),
     user = environment.config.validateAndGetString("storage.user"),
     pass=  environment.config.validateAndGetString("storage.pass"))
}
}
data class ConfigParameters(
    val url : String,
    val driver:String,
    val user:String,
    val pass:String
)
fun ApplicationConfig.validateAndGetString(key: String): String {
    val property = propertyOrNull(key)?.getString()?.trim()
    if (property.isNullOrBlank()) {
        throw ApplicationConfigurationException("$key getting null or Empty from config")
    } else {
        return property
    }
}