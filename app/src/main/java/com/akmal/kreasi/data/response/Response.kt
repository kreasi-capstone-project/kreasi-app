package com.akmal.kreasi.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


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
    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String? = null
)

data class LearningPathDetailResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val data: SubjectWrapper
)

data class SubjectWrapper(
    @field:SerializedName("subjects")
    val data: Subject
)


data class AssessmentTest(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val data: AssessmentData
)

data class AssessmentData(
    @field:SerializedName("subjects")
    val subjects: Subject,

    @field:SerializedName("assesments")
    val assessments: Map<String, AssessmentDetail>
)

data class AssessmentDetail(
    @field:SerializedName("answers")
    val answers: List<String>,

    @field:SerializedName("correct_answers")
    val correctAnswers: String
)

@Parcelize
data class PostRequest(
    @field:SerializedName("score")
    val score: Int,

    @field:SerializedName("skill")
    val skill: String
): Parcelable

@Parcelize
data class PostResponse(
    @field:SerializedName("predicted_courses")
    val predictedCourses: List<String>?,

    @field:SerializedName("user_level")
    val userLevel: String?
): Parcelable


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