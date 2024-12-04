package com.akmal.kreasi.data.retrofit

import com.akmal.kreasi.data.response.LoginRequest
import com.akmal.kreasi.data.response.LoginResponse
import com.akmal.kreasi.data.response.RegisterRequest
import com.akmal.kreasi.data.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/signin")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}