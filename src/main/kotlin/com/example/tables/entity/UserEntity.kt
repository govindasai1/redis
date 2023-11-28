package com.example.tables.entity

import com.example.tables.UserTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(UserTable)

    var userName by UserTable.userName
    var password by UserTable.Password
    var email by UserTable.email
    var phoneNumber by UserTable.phoneNumber
}