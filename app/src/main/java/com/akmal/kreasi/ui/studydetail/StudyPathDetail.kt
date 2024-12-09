package com.akmal.kreasi.ui.studydetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akmal.kreasi.R
import com.akmal.kreasi.ViewModelFactory
import com.akmal.kreasi.data.response.Subject
import com.akmal.kreasi.databinding.ActivityStudyPathDetailBinding
import com.akmal.kreasi.ui.assessment.AssessmentActivity

class StudyPathDetail : AppCompatActivity() {
    private val viewModel by viewModels<StudyPathViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var _binding: ActivityStudyPathDetailBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStudyPathDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_sky_blue)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val studyId = intent.getIntExtra("LEARNING_ID", 0)
        if (studyId != 0) {
            viewModel.getStudyDetail(studyId)
        }

        viewModel.isLoading.observe(this) {isLoading ->
            showLoading(isLoading)
        }

        viewModel.studyDetail.observe(this) {studies ->
            studies.onSuccess { study ->
                Log.d("StudyDetailSuccess", "Success $study")
                displayDetailStudy(study)
            }
            studies.onFailure { error ->
                Log.d("studyDetailError", "Error $error")
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding?.btnTakeTest?.setOnClickListener {
            startActivity(Intent(this, AssessmentActivity::class.java))
        }
    }

    private fun displayDetailStudy(study: Subject) {
        binding?.title?.text = study.name
        binding?.description?.text = study.description

        val imgResource = when (study.name) {
            "Cybersecurity" -> R.drawable.artificial_intelligence
            "Data Analytics" -> R.drawable.python
            else -> R.drawable.atom
        }
        binding?.imgItemPhoto?.setImageResource(imgResource)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}