package com.akmal.kreasi.ui.mycustomview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.akmal.kreasi.R

class MyEmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                validateEmail(s.toString())
            }
        })
    }

    private fun validateEmail(input: String) {
        if (input.isEmpty()) {
            setError(context.getString(R.string.email_blank_message), null)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            setError(context.getString(R.string.email_format_message), null)
        } else {
            error = null
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return false
    }
}