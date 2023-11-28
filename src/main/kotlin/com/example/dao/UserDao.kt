package com.example.dao

import com.example.models.RegisterDetails
import com.example.models.UserLoginDetails
import java.util.UUID

interface UserDao {
    suspend fun userRegistration(registerDetails: RegisterDetails): Boolean
    suspend fun userLogin(userLogin: UserLoginDetails): UUID?
    suspend fun getDetails(id: UUID): RegisterDetails
    suspend fun deleteUser(id:UUID):Boolean

}