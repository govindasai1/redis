package com.example.testRepository

import com.example.config.Config
import com.example.di.koinModule
import com.example.exception.CommonException
import com.example.exception.NoUserException
import com.example.repository.UserDaoImp
import com.example.tables.UserTable
import com.example.testUtils.mock.TestDatabase
import com.example.testUtils.mock.Mockk
import com.example.testUtils.testParameters.loginDetails
import com.example.testUtils.testParameters.loginDetailsFailure
import com.example.testUtils.testParameters.registerDetails
import com.example.testUtils.testParameters.registrationDetails
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.mockk.MockKAnnotations
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserDaoImpTest {
    private val userDaoImpObject = UserDaoImp()

    @Before
    fun start() {
        createTestEnvironment{
            config = ApplicationConfig("test.conf")
            Config.init(this.build {  })
        }
        MockKAnnotations.init(this)
        TestDatabase.init()
        Mockk.init()
    }

    @After
    fun end() {
        TestDatabase.destroyTestDB()
    }

    @Test
    fun userRegistrationTest():Unit = runBlocking {
        val result = userDaoImpObject.userRegistration(registrationDetails)
        assertTrue(result)
    }

    @Test
    fun userRegistrationTestException(): Unit = runBlocking {
        try {
            userDaoImpObject.userRegistration(registerDetails)
        } catch (e: CommonException) {
            assertEquals(e.text, "Cannot Insert other user with same name")
        }
    }

    @Test
    fun userLoginTest(): Unit = runBlocking {
        val result = userDaoImpObject.userLogin(loginDetails)
        assertTrue(result != null)
    }

    @Test
    fun userLoginTestFailure(): Unit = runBlocking {
        val result = userDaoImpObject.userLogin(loginDetailsFailure)
        assertTrue(result == null)
    }

    @Test
    fun getDetailsTest(): Unit = runBlocking {
        val uuid = userDaoImpObject.userLogin(loginDetails)
        if (uuid != null) {
            val result = userDaoImpObject.getDetails(uuid)
            assertTrue(result.toString().isNotEmpty())
        }
    }

    @Test
    fun deleteUserTest():Unit = runBlocking {
        val uuid = userDaoImpObject.userLogin(loginDetails)
        if (uuid != null) {
            val result = userDaoImpObject.deleteUser(uuid)
            assertTrue(result)
        }
    }

    @Test
    fun deleteUserFailureTest():Unit = runBlocking {
        try {
            val uuid = UUID.randomUUID()
            userDaoImpObject.deleteUser(uuid)
        }catch (e:NoUserException){
            assertEquals(e.text,"User Not Found To Delete")
        }

    }
}