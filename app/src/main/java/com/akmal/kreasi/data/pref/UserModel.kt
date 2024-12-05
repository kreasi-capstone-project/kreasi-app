package com.akmal.kreasi.data.pref

data class UserModel(
    val id: String,
    val name: String? = null,
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)