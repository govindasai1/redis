package com.example.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object UserTable : UUIDTable("UserTable") {
    val userName = varchar("UserName", 50).uniqueIndex()
    val Password = varchar("Password", 50)
    val email = varchar("Email", 50).uniqueIndex()
    val phoneNumber = long("PhoneNumber").uniqueIndex()
}