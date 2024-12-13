package com.akmal.kreasi.data.di

import android.content.Context
import com.akmal.kreasi.data.UserRepository
import com.akmal.kreasi.data.pref.UserPreference
import com.akmal.kreasi.data.pref.dataStore
import com.akmal.kreasi.data.retrofit.ApiConfig
import com.akmal.kreasi.data.retrofit.ApiService
import com.akmal.kreasi.data.retrofit.ApiServiceRecommendation

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }

    fun provideApiService(token: String? = null): ApiService {
        return if(token.isNullOrEmpty()){
            ApiConfig.getApiService()
        } else {
            ApiConfig.getApiService(token)
        }
    }

    fun provideApiServiceRecommendation(token: String? = null): ApiServiceRecommendation {
        return if (token.isNullOrEmpty()) {
            ApiConfig.getApiServiceRecommedation()
        } else {
            ApiConfig.getApiServiceRecommedation(token)
        }
    }
}