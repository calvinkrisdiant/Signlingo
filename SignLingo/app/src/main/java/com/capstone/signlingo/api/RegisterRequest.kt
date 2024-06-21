package com.capstone.signlingo.api

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
)

data class RegisterResponse(
    val status: String?,
    val message: String,
    val data: Data?
)

data class Data(
    val userId: String?
)
