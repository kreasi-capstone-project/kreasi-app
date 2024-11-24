package com.akmal.kreasi.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akmal.kreasi.History
import com.akmal.kreasi.HistoryAdapter
import com.akmal.kreasi.LearningPath
import com.akmal.kreasi.LearningPathAdapter
import com.akmal.kreasi.R
import com.akmal.kreasi.databinding.FragmentHistoryBinding
import com.akmal.kreasi.ui.StudyPathDetail
import com.akmal.kreasi.ui.TestResult

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val list = ArrayList<History>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val historyViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root



//        val textView: TextView = binding.textDashboard
//        historyViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHistory.setHasFixedSize(true)
        list.addAll(getListHistory())
        showRecyclerList()
    }


    private fun getListHistory(): ArrayList<History> {
        val dataTitle = resources.getStringArray(R.array.data_name)
        val dataDate = resources.getStringArray(R.array.data_date)
        val listHistory = ArrayList<History>()
        for(i in dataTitle.indices) {
            val history = History(dataTitle[i], dataDate[i])
            listHistory.add(history)
        }
        return listHistory
    }

    private fun showRecyclerList() {
        binding.rvHistory.layoutManager = LinearLayoutManager(context)
        val listHistoryAdapter = HistoryAdapter(list)
        listHistoryAdapter.setOnItemClickCallback(object: HistoryAdapter.OnItemClickCallback{
            override fun onItemClicked(data: History) {
                val intent = Intent(requireContext(), TestResult::class.java)
                startActivity(intent)
            }
        })
        binding.rvHistory.adapter = listHistoryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}