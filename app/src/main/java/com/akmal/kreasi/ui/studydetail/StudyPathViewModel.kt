package com.akmal.kreasi.ui.studydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akmal.kreasi.data.UserRepository
import com.akmal.kreasi.data.response.Subject
import com.akmal.kreasi.data.retrofit.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StudyPathViewModel(private val repository: UserRepository, private val apiService: ApiService): ViewModel() {
    private val _studyDetail = MutableLiveData<Result<Subject>>()
    val studyDetail: LiveData<Result<Subject>> get() = _studyDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getStudyDetail(studyId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val userSession = repository.getSession().first()
                val token = userSession.token

                if (token.isNotEmpty()) {
                    val response = apiService.getLearningPathDetail("Bearer $token", studyId)
                    if (response.isSuccessful){
                        val responseBody = response.body()
                        if (responseBody?.status == "success") {
                            _studyDetail.postValue(Result.success(responseBody.data.data))
                        } else {
                            _studyDetail.postValue(Result.failure(Exception("Failed to fetch data")))
                        }
                    } else{
                        _studyDetail.postValue(Result.failure(Exception(response.message())))
                    }
                }
            } catch (e: Exception) {
                _studyDetail.postValue(Result.failure(e))
            } finally {
                _isLoading.value = false
            }
        }
    }
}