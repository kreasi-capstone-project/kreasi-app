package com.akmal.kreasi.data.question

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)