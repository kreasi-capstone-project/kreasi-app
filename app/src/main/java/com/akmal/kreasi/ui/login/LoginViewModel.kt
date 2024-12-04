package com.akmal.kreasi.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akmal.kreasi.data.UserRepository
import com.akmal.kreasi.data.pref.UserModel
import com.akmal.kreasi.data.response.LoginRequest
import com.akmal.kreasi.data.retrofit.ApiService
import com.akmal.kreasi.helper.ApiErrorHandler
import kotlinx.coroutines.launch


class LoginViewModel(private val apiService: ApiService, private val repository: UserRepository): ViewModel() {
    private val _loginState = MutableLiveData<Result<UserModel>>()
    val loginState: LiveData<Result<UserModel>> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = apiService.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.status == "success" && responseBody.data != null) {
                        val token = responseBody.data.token
                        val user = UserModel(
                            email = responseBody.data.users.email,
                            token = token,
                            isLogin = true
                        )

                        repository.saveSession(user)
                        Log.d("LoginViewModel", "Session saved for user: $user")

                        _loginState.postValue(Result.success(user))
                        Log.d("LoginViewModel", "Login success, user state updated")
                    }
                    else {
                        val errorMessage = responseBody?.status ?: "Unknown error"
                        Log.e("LoginViewModel", "Login failed: $errorMessage")
                        _loginState.postValue(Result.failure(Exception(errorMessage)))
                    }
                }
                else {
                    val errorMessage = ApiErrorHandler.parseError(response)
                    _loginState.postValue(Result.failure(Exception(errorMessage)))
                }
            } catch (e: Exception) {
                val errorMessage = ApiErrorHandler.handleError(e)
                _loginState.postValue(Result.failure(Exception(errorMessage)))
            }
        }
    }
}