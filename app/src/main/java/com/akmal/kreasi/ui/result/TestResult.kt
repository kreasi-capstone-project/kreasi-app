package com.akmal.kreasi.ui.result

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akmal.kreasi.R
import com.akmal.kreasi.RecommendationLearningAdapter
import com.akmal.kreasi.databinding.ActivityTestResultBinding

class TestResult : AppCompatActivity() {
    private lateinit var binding: ActivityTestResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load data from res/values/strings.xml
        val data = loadExpandableListData()

        // Setup ExpandableListView
        setupExpandableListView(data)
    }

    private fun loadExpandableListData(): HashMap<String, List<String>> {
        val data = HashMap<String, List<String>>()

        // Get categories and items from strings.xml
        val categories = resources.getStringArray(R.array.header_data)
        val cricketTeams = resources.getStringArray(R.array.child_data_0).toList()
        val footballTeams = resources.getStringArray(R.array.child_data_1).toList()
        val basketballTeams = resources.getStringArray(R.array.child_data_2).toList()

        // Map data to HashMap
        data[categories[0]] = cricketTeams
        data[categories[1]] = footballTeams
        data[categories[2]] = basketballTeams

        return data
    }

    private fun setupExpandableListView(data: HashMap<String, List<String>>) {
        val titles = ArrayList(data.keys)
        val adapter = RecommendationLearningAdapter(this, titles, data)
        binding.expandableListView.setAdapter(adapter)

        // ExpandableListView event listeners
        binding.expandableListView.setOnGroupExpandListener { groupPosition ->
            Toast.makeText(
                this,
                "${titles[groupPosition]} List Expanded.",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.expandableListView.setOnGroupCollapseListener { groupPosition ->
            Toast.makeText(
                this,
                "${titles[groupPosition]} List Collapsed.",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.expandableListView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            Toast.makeText(
                this,
                "${titles[groupPosition]} -> ${data[titles[groupPosition]]!![childPosition]}",
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }
}