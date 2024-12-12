package com.akmal.kreasi.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.akmal.kreasi.R
import com.akmal.kreasi.ViewModelFactory
import com.akmal.kreasi.databinding.ActivityLoginBinding
import com.akmal.kreasi.ui.main.MainActivity
import com.akmal.kreasi.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textActionRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        viewModel.loginState.observe(this) { result ->
            Log.d("loginResult", "result $result")
            if (!isFinishing && !isDestroyed) {
                result.onSuccess { user ->
                    Log.d("ShowOnSuccess", "user $user")
                    showAlertDialog(getString(R.string.title_login_success),
                        getString(R.string.message_login_success), true)
                }
                result.onFailure { exception ->
                    val errorMessage = exception.message ?: getString(R.string.error_message)
                    Log.d("ShowOnFailure", "exception $errorMessage")
                    showAlertDialog(getString(R.string.title_login_failed), errorMessage, false)
                }
            }
        }

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun showAlertDialog(title: String, message: String, isSuccess: Boolean) {
        Log.d("showAlertDialog", "Title: $title, Message: $message")
        if (isFinishing || isDestroyed) return

        alertDialog?.dismiss()
        val dialogBuilder = AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
        dialogBuilder.apply {
            setTitle(title)
            setMessage(message)
            if (isSuccess) {
                setPositiveButton(getString(R.string.continue_text)) { dialog, _ ->
                    dialog.dismiss()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            } else {
                setPositiveButton(getString(R.string.try_again)) { dialog, _, ->
                    dialog.dismiss()
                }
            }

            alertDialog = dialogBuilder.create()
            alertDialog?.show()
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (password.length < 8) {
                binding.edLoginPassword.error = getString(R.string.message_error_password)
                return@setOnClickListener
            }
            viewModel.login(email, password)
        }
    }

    override fun onDestroy() {
        alertDialog?.dismiss()
        viewModel.loginState.removeObservers(this)
        super.onDestroy()
    }
}