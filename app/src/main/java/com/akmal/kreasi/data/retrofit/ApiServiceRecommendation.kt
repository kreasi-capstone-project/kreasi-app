package com.akmal.kreasi.data.retrofit

import com.akmal.kreasi.data.response.PostRequest
import com.akmal.kreasi.data.response.PostResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceRecommendation {
    @POST("predict")
    suspend fun submitTestResult(
        @Body request: PostRequest
    ): PostResponse
}