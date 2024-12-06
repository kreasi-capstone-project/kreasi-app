package com.akmal.kreasi.data.response

import com.google.gson.annotations.SerializedName


data class RegisterRequest(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)

data class RegisterResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val data: RegisterData
)

data class RegisterData(
    @field:SerializedName("users")
    val users: User,

    @field:SerializedName("token")
    val token: String
)

data class User(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String
)

data class LoginRequest(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)

data class LoginResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val data: LoginData,
)

data class LoginData(
    @field:SerializedName("user")
    val users: UserData,

    @field:SerializedName("token")
    val token: String
)

data class UserData(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("name")
    val name: String
)

data class LearningPathResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val data: Subjects
)

data class Subjects(
    @field:SerializedName("subjects")
    val subjects: List<Subject>
)

data class Subject(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String
)


data class ApiErrorResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("errors")
    val error: ErrorDetails
)

data class ErrorDetails(
    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String,

    @field: SerializedName("details")
    val details: Map<String, String>? = null
)