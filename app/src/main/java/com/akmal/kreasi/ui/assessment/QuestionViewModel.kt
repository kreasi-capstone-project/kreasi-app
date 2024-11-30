package com.akmal.kreasi.ui.assessment

import androidx.lifecycle.ViewModel
import com.akmal.kreasi.data.question.Question

class QuestionViewModel(): ViewModel() {
    val questions = listOf(
        Question(
            questionText = "Manakah dari algoritma berikut yang paling cocok untuk melakukan klasifikasi?",
            options = listOf("K-Means Clustering", "Linear Regression", "Decision Tree", "Principal Component Analysis (PCA)"),
            correctAnswerIndex = 2
        ),
        Question(
            questionText = "Apa output dari algoritma Decision Tree?",
            options = listOf("Cluster", "Decision Rules", "Centroid", "Principal Components"),
            correctAnswerIndex = 1
        )
    )

    var currentQuestionIndex: Int = 0
    val userAnswers = mutableMapOf<Int, Int>() // Menyimpan jawaban pengguna
}