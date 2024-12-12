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
        supportActionBar?.title = "Recommendation Learning"

        val data = loadExpandableListData()

        setupExpandableListView(data)
    }

    private fun loadExpandableListData(): HashMap<String, List<String>> {
        val data = HashMap<String, List<String>>()

        // Get categories and items from strings.xml
        val categories = resources.getStringArray(R.array.header_data)
        val data1 = resources.getStringArray(R.array.child_data_0).toList()
        val data2 = resources.getStringArray(R.array.child_data_1).toList()
        val data3 = resources.getStringArray(R.array.child_data_2).toList()
        val data4 = resources.getStringArray(R.array.child_data_4).toList()
        val data5 = resources.getStringArray(R.array.child_data_5).toList()
        val data6 = resources.getStringArray(R.array.child_data_6).toList()
        val data7 = resources.getStringArray(R.array.child_data_7).toList()
        val data8 = resources.getStringArray(R.array.child_data_8).toList()
        val data9 = resources.getStringArray(R.array.child_data_9).toList()
        val data10 = resources.getStringArray(R.array.child_data_10).toList()

        // Map data to HashMap
        data[categories[0]] = data1
        data[categories[1]] = data2
        data[categories[2]] = data3
        data[categories[3]] = data4
        data[categories[4]] = data5
        data[categories[5]] = data6
        data[categories[6]] = data7
        data[categories[7]] = data8
        data[categories[8]] = data9
        data[categories[9]] = data10

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