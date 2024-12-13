package com.akmal.kreasi.ui.assessment

import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.akmal.kreasi.R
import com.akmal.kreasi.ViewModelFactory
import com.akmal.kreasi.data.question.Question
import com.akmal.kreasi.databinding.FragmentQuestionBinding
import com.akmal.kreasi.ui.result.TestResult
import kotlin.math.log

class QuestionFragment : Fragment() {
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuestionViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private val viewModelRecommend: QuestionPostViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val studyId = arguments?.getInt("LEARNING_ID", 0)
        Log.d("studyId", "cek studyId $studyId")
        if (studyId != 0) {
            viewModel.getQuestions(studyId)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
            showLoading(isLoading)
        }

        viewModel.question.observe(viewLifecycleOwner) {result ->
            Log.d("cek result", "result $result")
            result.onSuccess { questions ->
                Log.d("questions", "question success $questions")
                displayQuestion(questions[viewModel.currentQuestionIndex])
                updateNavigationButtons(questions.size)
            }
            result.onFailure { error ->
                Log.d("error", "question error $error")
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        }


        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedAnswerIndex = when (checkedId) {
                binding.radioOption1.id -> 0
                binding.radioOption2.id -> 1
                binding.radioOption3.id -> 2
                binding.radioOption4.id -> 3
                else -> -1
            }
            viewModel.userAnswers[viewModel.currentQuestionIndex] = selectedAnswerIndex
        }

        binding.btnNext.setOnClickListener {
            val questions = viewModel.question.value?.getOrNull()
            if (questions != null) {
                if (viewModel.currentQuestionIndex < questions.size - 1) {
                    viewModel.currentQuestionIndex++
                    displayQuestion(viewModel.question.value?.getOrNull()?.get(viewModel.currentQuestionIndex)!!)
                    updateNavigationButtons(questions.size)
                } else {
                    showAlertDialog()
                }
            }
        }

        binding.btnPrevious.setOnClickListener {
            if (viewModel.currentQuestionIndex > 0) {
                viewModel.currentQuestionIndex--
                val questions = viewModel.question.value?.getOrNull()
                if (questions != null) {
                    displayQuestion(questions[viewModel.currentQuestionIndex])
                    updateNavigationButtons(questions.size)
                }
            }
        }
    }

    private fun displayQuestion(question: Question) {
        binding.tvQuestion.text = question.questionText
        binding.radioOption1.text = question.options[0]
        binding.radioOption2.text = question.options[1]
        binding.radioOption3.text = question.options[2]
        binding.radioOption4.text = question.options[3]

        binding.radioGroup.setOnCheckedChangeListener(null)

        val selectedAnswer = viewModel.userAnswers[viewModel.currentQuestionIndex]
        if (selectedAnswer != null) {
            when(selectedAnswer) {
                0 -> binding.radioGroup.check(binding.radioOption1.id)
                1 -> binding.radioGroup.check(binding.radioOption2.id)
                2 -> binding.radioGroup.check(binding.radioOption3.id)
                3 -> binding.radioGroup.check(binding.radioOption4.id)
            }
        } else {
            binding.radioGroup.clearCheck()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedAnswerIndex = when(checkedId) {
                binding.radioOption1.id -> 0
                binding.radioOption2.id -> 1
                binding.radioOption3.id -> 2
                binding.radioOption4.id -> 3
                else -> -1
            }
            viewModel.userAnswers[viewModel.currentQuestionIndex] = selectedAnswerIndex
        }
    }

    private fun updateNavigationButtons(totalQuestions: Int) {
        if (viewModel.currentQuestionIndex == 0) {
            binding.btnPrevious.visibility = View.GONE
        } else {
            binding.btnPrevious.visibility = View.VISIBLE
        }

        if (viewModel.currentQuestionIndex == totalQuestions - 1) {
            binding.btnNext.text = "Finish"
        } else {
            binding.btnNext.text = "Next"
        }
    }

    private fun showAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogTheme)
        dialogBuilder.apply {
            setTitle("Finish Test")
            setMessage("Are you sure you want to finish the test? Once completed, you cannot go back.")
            setPositiveButton("Yes, Finish") {dialog, _, ->
                dialog.dismiss()
                val nameStudy = arguments?.getString("NAME_LEARNING")
                Log.d("nameStudy", "cek namestudy $nameStudy")
                val correctAnswers = calculateCorrectAnswers()
                Log.d("correnctAnswer", "cek correctAnswers $correctAnswers")

                if (nameStudy != null) {
                    viewModelRecommend.submitTestResult(correctAnswers, nameStudy)
                }

                viewModelRecommend.postResponse.observe(viewLifecycleOwner) {result ->
                    result.onSuccess { recommendation ->
                        val intent = Intent(requireContext(), TestResult::class.java)
                        intent.putExtra("RECOMMENDATION_DATA", recommendation)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    result.onFailure { error ->
                        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
            setNegativeButton("Cancel") {dialog, _ ->
                dialog.dismiss()
            }
        }
        dialogBuilder.create().show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun calculateCorrectAnswers(): Int {
        return viewModel.question.value?.getOrNull()?.count { question ->
            val userAnswer = viewModel.userAnswers[question.correctAnswerIndex]
            question.correctAnswerIndex == userAnswer
        } ?: 0
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(subjectId: Int, nameStudy: String) = QuestionFragment().apply {
            arguments = Bundle().apply {
                putInt("LEARNING_ID", subjectId)
                putString("NAME_LEARNING", nameStudy)
            }
        }
    }
}