package com.example.testUtils.mock

import com.example.tables.UserTable
import com.example.tables.entity.UserEntity
import com.example.testUtils.testParameters.registerDetails
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

object Mockk {
    fun init(){
        transaction {
            UserTable.deleteAll()
            UserEntity.new {
                userName = registerDetails.userName
                password = registerDetails.password
                email = registerDetails.email
                phoneNumber = registerDetails.phoneNumber
            }

        }
    }
}