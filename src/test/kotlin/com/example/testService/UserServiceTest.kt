package com.example.testService

import com.example.config.Config
import com.example.di.koinModule
import com.example.exception.CommonException
import com.example.exception.NoUserException
import com.example.service.UserService
import com.example.tables.UserTable
import com.example.testUtils.mock.TestDatabase
import com.example.testUtils.mock.Mockk
import com.example.testUtils.testParameters.loginDetails
import com.example.testUtils.testParameters.loginDetailsFailure
import com.example.testUtils.testParameters.registrationDetails
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.mockk.MockKAnnotations
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Suppress("SENSELESS_COMPARISON")
class UserServiceTest : KoinComponent {
    private val userServiceOnj = UserService()

    @Before
    fun start() {
        createTestEnvironment{
            config = ApplicationConfig("test.conf")
            Config.init(this.build {  })
        }
        startKoin { modules(koinModule) }
        TestDatabase.init()
        Mockk.init()
    }

    @After
    fun end() {
        stopKoin()
       TestDatabase.destroyTestDB()
    }

    @Test
    fun userServiceRegisterTest() = runBlocking {
        val result = userServiceOnj.registerUser(registrationDetails)
        assertEquals(result.text, "User Registered Successfully")
    }

    @Test
    fun userServiceRegisterTestException() = runBlocking {
        try {
            val result = userServiceOnj.registerUser(registrationDetails)
            assertEquals(result.text, "User Registered Successfully")
        } catch (e: CommonException) {
            assertEquals(e.text, "Cannot Insert other user with same name")
        }
    }

    @Test
    fun loginUserServiceTest() = runBlocking {
        val result = userServiceOnj.loginUser(loginDetails)
        assertTrue(result != null)
    }

    @Test
    fun loginUserServiceFailureTest() = runBlocking {
        val result = userServiceOnj.loginUser(loginDetailsFailure)
        assertTrue(result == null)
    }

    @Test
    fun userDetailsServiceTest() = runBlocking {
        val result = userServiceOnj.loginUser(loginDetails)
        if (result != null) {
            val response = userServiceOnj.userDetails(result)
            assertTrue(response != null)
        }
    }

    @Test
    fun userDetailsServiceTestException() = runBlocking {
        try {
        val result = userServiceOnj.loginUser(loginDetailsFailure)
        if (result != null) {
            val response = userServiceOnj.userDetails(result)
            assertTrue(response != null)
        }
    }catch (e:CommonException){
        assertEquals(e.text,"No User Found")
    }
    }

    @Test
    fun userDeleteServiceTest() = runBlocking {
        try {
            val result = userServiceOnj.loginUser(loginDetailsFailure)
            if (result != null) {
                val response = userServiceOnj.deleteUser(result)
                assertEquals(response.text, "User Deleted Successfully")
            }
        }catch (e: NoUserException){
            assertEquals(e.text,"User Not Found To Delete")
        }
    }
}