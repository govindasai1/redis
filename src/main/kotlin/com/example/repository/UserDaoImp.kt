package com.example.repository

import com.example.dao.UserDao
import com.example.database.DatabaseFactory
import com.example.exception.CommonException
import com.example.exception.NoUserException
import com.example.models.RegisterDetails
import com.example.models.UserLoginDetails
import com.example.tables.UserTable
import com.example.tables.entity.UserEntity
import org.jetbrains.exposed.sql.and
import java.util.*

class UserDaoImp : UserDao {
    override suspend fun userRegistration(registerDetails: RegisterDetails): Boolean {
        try {
            return DatabaseFactory.dbQuery {
                UserEntity.new {
                    userName = registerDetails.userName
                    password = registerDetails.password
                    email = registerDetails.password
                    phoneNumber = registerDetails.phoneNumber
                }
            }.id.toString().isNotEmpty()

        } catch (e: Exception) {
            throw CommonException(text = "Cannot Insert other user with same name")
        }
    }

    override suspend fun userLogin(userLogin: UserLoginDetails): UUID? {
        return DatabaseFactory.dbQuery {
            UserEntity.find { UserTable.userName.eq(userLogin.userName) and UserTable.Password.eq(userLogin.password) }
                .map { it.id.value }.singleOrNull()
        }
    }

    override suspend fun getDetails(id: UUID): RegisterDetails {
        return DatabaseFactory.dbQuery {
            UserEntity.find { UserTable.id.eq(id) }
                .map { RegisterDetails(it.userName, it.password, it.email, it.phoneNumber) }.first()
        }
    }

    override suspend fun deleteUser(id: UUID): Boolean {
        try {
            DatabaseFactory.dbQuery {
                UserEntity[id].delete()
            }
            return true
        }catch (e:Exception){
            throw NoUserException("User Not Found To Delete")
        }
    }


}