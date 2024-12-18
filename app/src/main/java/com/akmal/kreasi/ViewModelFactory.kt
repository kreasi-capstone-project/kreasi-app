package com.akmal.kreasi

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akmal.kreasi.data.UserRepository
import com.akmal.kreasi.data.di.Injection
import com.akmal.kreasi.data.retrofit.ApiService
import com.akmal.kreasi.data.retrofit.ApiServiceRecommendation
import com.akmal.kreasi.ui.assessment.QuestionPostViewModel
import com.akmal.kreasi.ui.assessment.QuestionViewModel
import com.akmal.kreasi.ui.home.HomeViewModel
import com.akmal.kreasi.ui.login.LoginViewModel
import com.akmal.kreasi.ui.register.RegisterViewModel
import com.akmal.kreasi.ui.setting.SettingViewModel
import com.akmal.kreasi.ui.studydetail.StudyPathViewModel

class ViewModelFactory(private val repository: UserRepository, private val apiService: ApiService, private val apiServiceRecommendation: ApiServiceRecommendation) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository, apiService) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(apiService, repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(apiService) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(StudyPathViewModel::class.java) -> {
                StudyPathViewModel(repository, apiService) as T
            }
            modelClass.isAssignableFrom(QuestionViewModel::class.java) -> {
                QuestionViewModel(repository, apiService) as T
            }
            modelClass.isAssignableFrom(QuestionPostViewModel::class.java) -> {
                QuestionPostViewModel(apiServiceRecommendation) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context, token: String? = null): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    val repository = Injection.provideRepository(context)
                    val apiService = Injection.provideApiService(token)
                    val apiServiceRecommendation = Injection.provideApiServiceRecommendation(token)
                    INSTANCE = ViewModelFactory(repository, apiService, apiServiceRecommendation)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}