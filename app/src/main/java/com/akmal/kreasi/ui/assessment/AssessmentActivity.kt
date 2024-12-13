package com.akmal.kreasi.ui.assessment

import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@AssessmentActivity, R.style.MyAlertDialogTheme).apply {
                    setTitle("Leave Test")
                    setMessage("Are you sure you want to leave this test? Your progress will not be saved.")
                    setPositiveButton("Leave") {dialog, _, ->
                        dialog.dismiss()
                        finish()
                    }
                    setNegativeButton("cancel") {dialog, _ ->
                        dialog.dismiss()
                    }
                }.create().show()
            }
        })
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_sky_blue)

        supportActionBar?.title = "Assessment Test"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val studyId = intent.getIntExtra("LEARNING_ID", 0)
        val nameLearning = intent.getStringExtra("NAME_LEARNING")
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_container, QuestionFragment.newInstance(studyId,
                    nameLearning.toString()
                ))
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        AlertDialog.Builder(this, R.style.MyAlertDialogTheme).apply {
            setTitle("Leave Test")
            setMessage("Are you sure you want to leave this test? Your progress will not be saved.")
            setPositiveButton("leave"){ dialog, _ ->
                dialog.dismiss()
                finish()
            }
            setNegativeButton("cancel") {dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
        return true
    }
}