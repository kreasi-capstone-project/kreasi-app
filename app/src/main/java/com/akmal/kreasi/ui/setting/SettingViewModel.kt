package com.akmal.kreasi.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.akmal.kreasi.data.UserRepository
import com.akmal.kreasi.data.pref.UserModel
import kotlinx.coroutines.launch

class SettingViewModel(private val repository: UserRepository) : ViewModel() {
//    private val _dataStory = MutableLiveData<Result<List<Story>>>()
//    val dataStory: LiveData<Result<List<Story>>> get() = _dataStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}