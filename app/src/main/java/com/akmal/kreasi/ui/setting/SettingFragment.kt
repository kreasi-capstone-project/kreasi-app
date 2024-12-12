package com.akmal.kreasi.ui.setting

import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.akmal.kreasi.R
import com.akmal.kreasi.ViewModelFactory
import com.akmal.kreasi.databinding.FragmentSettingBinding
import com.akmal.kreasi.ui.login.LoginActivity

class SettingFragment : Fragment() {
    private val viewModel by viewModels<SettingViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) {user ->
            if (user != null && user.isLogin) {
                binding.tvProfileName.text = user.name
                binding.tvProfileEmail.text = user.email
            }
        }

        binding.tvLogout.setOnClickListener {
            context?.let { it1 ->
                AlertDialog.Builder(it1, R.style.MyAlertDialogTheme).apply {
                    setTitle(getString(R.string.log_out_title))
                    setMessage(getString(R.string.Log_out_message))
                    setPositiveButton(getString(R.string.log_out_title)) { dialog, _ ->
                        dialog.dismiss()
                        viewModel.logout()
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    setNegativeButton(getString(R.string.cancel_text)) { dialog, _ ->
                        dialog.dismiss()
                    }
                }.create().show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}