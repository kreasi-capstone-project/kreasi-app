package com.akmal.kreasi.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akmal.kreasi.data.UserRepository
import com.akmal.kreasi.data.pref.UserModel
import com.akmal.kreasi.data.response.RegisterRequest
import com.akmal.kreasi.data.retrofit.ApiService
import com.akmal.kreasi.helper.ApiErrorHandler
import kotlinx.coroutines.launch

class RegisterViewModel(private val apiService: ApiService): ViewModel() {
    private val _registerResult = MutableLiveData<Result<String>>()
    val registerResult: LiveData<Result<String>> = _registerResult

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = apiService.register(RegisterRequest(name, email, password))
                if(response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("check response", "response $responseBody")
                        if (responseBody?.status == "success") {
                            _registerResult.postValue(Result.success("Account successfully created!"))
                        } else {
                            val errorMessage = responseBody?.status ?: "Unknown error"
                            Log.e("LoginViewModel", "Failed to create account: $errorMessage")
                            _registerResult.postValue(Result.failure(Exception(errorMessage)))
                        }
                    }
                else {
                    val errorMessage = ApiErrorHandler.parseError(response)
                    _registerResult.postValue(Result.failure(Exception(errorMessage)))
                }
            } catch (e: Exception) {
                val errorMessage = ApiErrorHandler.handleError(e)
                _registerResult.postValue(Result.failure(Exception(errorMessage)))
            }
        }
    }

}