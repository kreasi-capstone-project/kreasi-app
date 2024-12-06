package com.akmal.kreasi.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.akmal.kreasi.data.UserRepository
import com.akmal.kreasi.data.pref.UserModel
import com.akmal.kreasi.data.response.Subject
import com.akmal.kreasi.data.retrofit.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository, private val apiService: ApiService) : ViewModel() {
    private val _dataLearningPath = MutableLiveData<Result<List<Subject>>>()
    val dataLearningPath: LiveData<Result<List<Subject>>> get() = _dataLearningPath

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getAllLearningPath() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val userSession = repository.getSession().first()
                val token = userSession.token
                if (token.isNotEmpty()) {
                    val response = apiService.getAllLearningPath("Bearer $token")
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody?.status == "success") {
                            _dataLearningPath.postValue(Result.success(responseBody.data.subjects))
                        } else {
                            _dataLearningPath.postValue(Result.failure(Exception("Failed to fetch data")))
                        }
                    } else {
                        _dataLearningPath.postValue(Result.failure(Exception("User not logged in")))
                    }
                }
            } catch (e: Exception) {
                _dataLearningPath.postValue(Result.failure(e))
            } finally {
                _isLoading.value = false
            }
        }
    }
}