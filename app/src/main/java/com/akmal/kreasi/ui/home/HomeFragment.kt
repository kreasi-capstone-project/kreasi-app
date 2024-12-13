package com.akmal.kreasi.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.akmal.kreasi.LearningPathAdapter
import com.akmal.kreasi.ViewModelFactory
import com.akmal.kreasi.data.response.Subject
import com.akmal.kreasi.databinding.FragmentHomeBinding
import com.akmal.kreasi.ui.login.LoginActivity
import com.akmal.kreasi.ui.studydetail.StudyPathDetail

class HomeFragment : Fragment(), LearningPathAdapter.OnItemClickListener {
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var learningPathAdapter: LearningPathAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvLearningPath.layoutManager = layoutManager
        learningPathAdapter = LearningPathAdapter(this)
        binding.rvLearningPath.adapter = learningPathAdapter

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.dataLearningPath.observe(viewLifecycleOwner) { results ->
            Log.d("results", "results $results")
            results.onSuccess { learningPath ->
                Log.d("learningPath", "learningPath $learningPath")
                learningPathAdapter.submitList(learningPath)
            }
            results.onFailure { error ->
                Log.d("error", "Error $error")
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getAllLearningPath()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(data: Subject) {
        val intent = Intent(requireContext(), StudyPathDetail::class.java)
        intent.putExtra("LEARNING_ID", data.id)
        intent.putExtra("NAME_LEARNING", data.name)
        startActivity(intent)
    }
}