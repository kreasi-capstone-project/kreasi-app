package com.akmal.kreasi.ui.assessment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.akmal.kreasi.R
import com.akmal.kreasi.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuestionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val question = viewModel.questions[viewModel.currentQuestionIndex]

        // Tampilkan soal dan opsi
        binding.tvQuestion.text = question.questionText
        binding.radioOption1.text = question.options[0]
        binding.radioOption2.text = question.options[1]
        binding.radioOption3.text = question.options[2]
        binding.radioOption4.text = question.options[3]

        // Menyimpan jawaban yang dipilih
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

        // Navigasi soal berikutnya
        binding.btnNext.setOnClickListener {
            if (viewModel.currentQuestionIndex < viewModel.questions.size - 1) {
                viewModel.currentQuestionIndex++
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, QuestionFragment())
                    .commit()
            }
        }

        // Navigasi soal sebelumnya
        binding.btnPrevious.setOnClickListener {
            if (viewModel.currentQuestionIndex > 0) {
                viewModel.currentQuestionIndex--
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, QuestionFragment())
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }
}