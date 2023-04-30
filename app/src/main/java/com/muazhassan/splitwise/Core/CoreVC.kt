package com.muazhassan.splitwise.Core

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText

open class CoreVC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val rootLayout = findViewById<View>(android.R.id.content)
        rootLayout.setBackgroundColor(Color.CYAN)
    }

    fun validateRegistrationFields(vararg fields: EditText): Boolean {
        var isValid = true

        for (field in fields) {
            if (field.text.isNullOrEmpty()) {
                field.error = "Required."
                isValid = false
            } else {
                field.error = null
            }
        }

    }

}