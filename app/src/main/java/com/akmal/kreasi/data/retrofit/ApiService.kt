package com.akmal.kreasi.data.retrofit

import com.akmal.kreasi.data.response.LearningPathDetailResponse
import com.akmal.kreasi.data.response.LearningPathResponse
import com.akmal.kreasi.data.response.LoginRequest
import com.akmal.kreasi.data.response.LoginResponse
import com.akmal.kreasi.data.response.RegisterRequest
import com.akmal.kreasi.data.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/signin")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("api/subjects")
    suspend fun getAllLearningPath(
        @Header("Authorization") token: String,
    ): Response<LearningPathResponse>

    @GET("api/subjects/{id}")
    suspend fun getLearningPathDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<LearningPathDetailResponse>
}