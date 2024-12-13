package com.akmal.kreasi.ui.assessment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akmal.kreasi.data.response.PostRequest
import com.akmal.kreasi.data.response.PostResponse
import com.akmal.kreasi.data.retrofit.ApiServiceRecommendation
import kotlinx.coroutines.launch

class QuestionPostViewModel(private val apiServiceRecommendation: ApiServiceRecommendation): ViewModel() {
    val postResponse: MutableLiveData<Result<PostResponse>> = MutableLiveData()

    fun submitTestResult(score: Int, skill: String) {
        viewModelScope.launch {
            try {
                val response = apiServiceRecommendation.submitTestResult(PostRequest(score, skill))
                postResponse.value = Result.success(response)
            } catch (e: Exception) {
                postResponse.value = Result.failure(e)
            }
        }
    }

}