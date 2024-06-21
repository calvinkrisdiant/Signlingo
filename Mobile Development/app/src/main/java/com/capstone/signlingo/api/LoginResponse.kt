package com.capstone.signlingo.api

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(
    val message: String,
    val token: String?,
    val user: User?
)

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val password: String,
    val createdAt: String,
    val updatedAt: String
)
