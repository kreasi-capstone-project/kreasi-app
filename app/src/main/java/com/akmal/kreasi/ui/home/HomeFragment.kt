package com.akmal.kreasi.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akmal.kreasi.LearningPath
import com.akmal.kreasi.LearningPathAdapter
import com.akmal.kreasi.R
import com.akmal.kreasi.databinding.FragmentHomeBinding
import com.akmal.kreasi.ui.studydetail.StudyPathDetail

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val list = ArrayList<LearningPath>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvLearningPath.setHasFixedSize(true)
        list.addAll(getListLearningPath())
        showRecyclerList()
    }


    private fun getListLearningPath(): ArrayList<LearningPath> {
        val dataTitle = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listLearningPath = ArrayList<LearningPath>()
        for(i in dataTitle.indices) {
            val learningPath = LearningPath(dataTitle[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listLearningPath.add(learningPath)
        }
        dataPhoto.recycle()
        return listLearningPath
    }

    private fun showRecyclerList() {
        binding.rvLearningPath.layoutManager = LinearLayoutManager(context)
        val listLearningPathAdapter = LearningPathAdapter(list)
        listLearningPathAdapter.setOnItemClickCallback(object : LearningPathAdapter.OnItemClickCallback{
            override fun onItemClicked(data: LearningPath) {
                val intent = Intent(requireContext(), StudyPathDetail::class.java)
                startActivity(intent)
            }
        })
        binding.rvLearningPath.adapter = listLearningPathAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}