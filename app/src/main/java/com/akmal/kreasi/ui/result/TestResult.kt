package com.akmal.kreasi.ui.result

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akmal.kreasi.R
import com.akmal.kreasi.RecommendationLearningAdapter
import com.akmal.kreasi.data.response.PostResponse
import com.akmal.kreasi.databinding.ActivityTestResultBinding

class TestResult : AppCompatActivity() {
    private lateinit var binding: ActivityTestResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Recommendation Learning"

        // Retrieve data from intent
        val recommendation = intent.getParcelableExtra<PostResponse>("RECOMMENDATION_DATA")
        if (recommendation != null && recommendation.predictedCourses != null) {
            setupExpandableListView(recommendation)
        } else {
            Toast.makeText(this, "Recommendation data is unavailable", Toast.LENGTH_SHORT).show()
            finish() // Close the activity if data is missing
        }
    }

    private fun setupExpandableListView(recommendation: PostResponse) {
        val data = HashMap<String, List<String>>()

        // Map user_level as a header
        data["Your Level: ${recommendation.userLevel}"] = listOf()

        // Map predicted courses as items under a category
        data["Recommended Courses"] = recommendation.predictedCourses ?: listOf("No courses available")

        val titles = ArrayList(data.keys)
        val adapter = RecommendationLearningAdapter(this, titles, data)
        binding.expandableListView.setAdapter(adapter)

        // Optional: Add ExpandableListView event listeners (if needed)
        binding.expandableListView.setOnGroupExpandListener { groupPosition ->
            Toast.makeText(this, "${titles[groupPosition]} List Expanded.", Toast.LENGTH_SHORT).show()
        }
        binding.expandableListView.setOnGroupCollapseListener { groupPosition ->
            Toast.makeText(this, "${titles[groupPosition]} List Collapsed.", Toast.LENGTH_SHORT).show()
        }
        binding.expandableListView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            val item = data[titles[groupPosition]]?.get(childPosition)
            Toast.makeText(this, "Selected: $item", Toast.LENGTH_SHORT).show()
            false
        }
    }
}