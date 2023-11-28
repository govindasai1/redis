package com.example.service

import com.example.exception.CommonException
import com.example.models.Message
import com.example.models.RegisterDetails
import com.example.models.UserLoginDetails
import com.example.repository.UserDaoImp
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

class UserService : KoinComponent {
    private val userObj by inject<UserDaoImp>()

    suspend fun registerUser(registerDetails: RegisterDetails): Message {
        try {
            val result = userObj.userRegistration(registerDetails)
            return if (result) Message("User Registered Successfully")
            else Message("Not Registered")
        }catch (e:Exception){
            throw CommonException("Cannot Insert other user with same name")
        }
    }

    suspend fun loginUser(userLogin: UserLoginDetails): UUID? {
        return userObj.userLogin(userLogin)
    }

    suspend fun userDetails(id: UUID): RegisterDetails {
        try {
            return userObj.getDetails(id)
        } catch (e: Exception) {
            throw CommonException("No User Found")
        }
    }

    suspend fun deleteUser(id:UUID):Message{
        return if(userObj.deleteUser(id)) Message("User Deleted Successfully")
        else Message("No User Found")
    }
}