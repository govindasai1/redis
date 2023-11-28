package com.example.routeTest

import com.example.config.Config
import com.example.testUtils.mock.Mockk
import com.example.testUtils.mock.TestDatabase
import com.example.testUtils.testParameters.loginDetails
import com.example.testUtils.testParameters.registrationDetails
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.mockk.MockKAnnotations
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UserRouteTest {

    @Before
    fun start() {
        createTestEnvironment{
            config = ApplicationConfig("test.conf")
            Config.init(this.build {  })
        }
        TestDatabase.init()
        Mockk.init()
    }

    @After
    fun end() {
        TestDatabase.destroyTestDB()
    }

    @Test
    fun registerUser() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.post("/User" + "/Register") {
            headers[HttpHeaders.ContentType] = ContentType.Application.Json.toString()
            setBody(registrationDetails)
        }
        assertEquals(HttpStatusCode.Created, response.status)
    }

    @Test
    fun loginUser() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.post("/User" + "/Login") {
            headers[HttpHeaders.ContentType] = ContentType.Application.Json.toString()
            setBody(loginDetails)
        }
        println("--> ${response.bodyAsText()}")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun gettingData() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.get("/User" + "/getData"){
        }
        assertEquals(response.status,HttpStatusCode.OK)
    }
}