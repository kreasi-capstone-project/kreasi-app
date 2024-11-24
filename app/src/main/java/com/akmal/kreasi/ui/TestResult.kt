package com.akmal.kreasi.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akmal.kreasi.R
import com.akmal.kreasi.RecomendationLearningAdapter
import com.akmal.kreasi.databinding.ActivityTestResultBinding

class TestResult : AppCompatActivity() {
    private lateinit var binding: ActivityTestResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari strings.xml
        val headerData = resources.getStringArray(R.array.header_data).toList()
        val childData = mapOf(
            headerData[0] to resources.getStringArray(R.array.child_data_0).toList(),
            headerData[1] to resources.getStringArray(R.array.child_data_1).toList(),
            headerData[2] to resources.getStringArray(R.array.child_data_2).toList(),
            headerData[3] to resources.getStringArray(R.array.child_data_3).toList(),
            headerData[4] to resources.getStringArray(R.array.child_data_4).toList()
        )

        // Pasang adapter ke ExpandableListView
        val adapter = RecomendationLearningAdapter(this, headerData, childData)
        binding.expandableListView.setAdapter(adapter)
    }
}