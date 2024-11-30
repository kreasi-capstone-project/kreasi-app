package com.akmal.kreasi.ui.assessment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akmal.kreasi.R
import com.akmal.kreasi.databinding.ActivityAssessmentBinding

class AssessmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAssessmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_container, QuestionFragment())
                .commit()
        }
    }
}