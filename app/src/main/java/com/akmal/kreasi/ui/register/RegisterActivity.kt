package com.akmal.kreasi.ui.register

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
import com.akmal.kreasi.databinding.ActivityRegisterBinding
import com.akmal.kreasi.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityRegisterBinding
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textActionLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        viewModel.registerResult.observe(this) { result ->
            Log.d("result", "result $result")
            if (!isFinishing && !isDestroyed) {
                result.onSuccess { user ->
                    Log.d("user", "cek User$user")
                    showAlertDialog(getString(R.string.account_created),
                        getString(R.string.message_created_account), true)
                }
                result.onFailure { exception ->
                    Log.d("error", "check error $exception")
                    val errorMessage = exception.message ?: getString(R.string.error_message)
                    showAlertDialog(getString(R.string.register_failed), errorMessage, false)
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

    private fun showAlertDialog(title: String, message: String, isSuccess: Boolean) {
        if (isFinishing || isDestroyed) return

        val dialogBuilder = AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
        alertDialog?.dismiss()
        dialogBuilder.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(getString(R.string.continue_text)) { dialog, _ ->
                if (isSuccess) {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } else {
                    setPositiveButton(getString(R.string.try_again)) { _, _, ->
                        //do nothing
                    }
                }
            }
        }

        alertDialog = dialogBuilder.create()
        alertDialog?.show()
    }


    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            viewModel.register(name, email, password)
        }
    }

    override fun onDestroy() {
        alertDialog?.dismiss()
        viewModel.registerResult.removeObservers(this)
        super.onDestroy()
    }
}