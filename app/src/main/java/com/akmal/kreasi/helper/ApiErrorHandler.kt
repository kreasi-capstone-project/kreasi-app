package com.akmal.kreasi.helper

import com.akmal.kreasi.data.response.ApiErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ApiErrorHandler {
    fun parseError(response: Response<*>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            if (!errorBody.isNullOrEmpty()) {
                val errorResponse = Gson().fromJson(errorBody, ApiErrorResponse::class.java)
                errorResponse.error.message
            } else {
                "An unknown error occurred."
            }
        } catch (e: Exception) {
            "Failed to parse error response: ${e.localizedMessage}"
        }
    }

    fun handleError(exception: Exception): String {
        return when (exception) {
            is HttpException -> {
                val response = exception.response()
                response?.let { parseError(it) } ?: "HTTP error occurred: ${exception.message()}"
            }
            is UnknownHostException -> "Network connection error. Please check your internet."
            is SocketTimeoutException -> "The server took too long to respond. Please try again."
            else -> exception.message ?: "An unexpected error occurred."
        }
    }
}