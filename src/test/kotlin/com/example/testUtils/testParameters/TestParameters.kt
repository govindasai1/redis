package com.example.testUtils.testParameters

import com.example.models.RegisterDetails
import com.example.models.UserLoginDetails

val registrationDetails = RegisterDetails("govindasai1", "Govind@1230", "govnd101@gmail.com", 6925447754)
val registerDetails = RegisterDetails("govindasai", "Govind@1230", "govnd@gmail.com", 8688257665)
val loginDetails = UserLoginDetails("govindasai", "Govind@1230")
val loginDetail = UserLoginDetails("govindasai1", "Govind@1230")
val loginDetailsFailure = UserLoginDetails("govindasai0", "Govind@12300")

