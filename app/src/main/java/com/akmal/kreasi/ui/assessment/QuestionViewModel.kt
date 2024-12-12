package com.akmal.kreasi.ui.assessment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akmal.kreasi.data.UserRepository
import com.akmal.kreasi.data.question.Question
import com.akmal.kreasi.data.retrofit.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class QuestionViewModel(private val repository: UserRepository, private val apiService: ApiService): ViewModel() {
    private val _questions = MutableLiveData<Result<List<Question>>>()
    val question : LiveData<Result<List<Question>>> get() = _questions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    var currentQuestionIndex: Int = 0
    val userAnswers = mutableMapOf<Int, Int>()

    fun getQuestions(subjectId: Int?) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val userSession = repository.getSession().first()
                val token = userSession.token
                if (token.isNotEmpty()) {
                    val response = subjectId?.let { apiService.getAssessmentTest("Bearer $token", it) }
                    Log.d("cek response", "response $response")
                    if (response != null) {
                        if (response.isSuccessful) {
                            val responseBody = response?.body()
                            Log.d("cek responsebody", "responseBody $responseBody")
                            if (responseBody?.status == "success") {
                                val questionList = responseBody.data.assessments.map { (question, detail) ->
                                    Question(
                                        questionText = question,
                                        options = detail.answers,
                                        correctAnswerIndex = detail.answers.indexOf(detail.correctAnswers)
                                    )
                                }
                                Log.d("isi questionList", "questionlist $questionList")
                                _questions.postValue(Result.success(questionList))
                            } else {
                                _questions.postValue(Result.failure(Exception("Response body is null")))
                            }
                        } else {
                            _questions.postValue(Result.failure(Exception("Failed to fetch questions")))
                        }
                    }
                }
            } catch (e: Exception) {
                _questions.postValue(Result.failure(e))
            } finally {
                _isLoading.value = false
            }
        }
    }
}