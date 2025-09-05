package com.example.lab_week_02_b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    companion object {
        private const val COLOR_KEY = "COLOR_KEY"
        private const val ERROR_KEY = "ERROR_KEY"
    }

    private val submitButton: Button
        get() = findViewById(R.id.submit_button)

    // Activity Result API
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_CANCELED) {
            val data = result.data
            val errorMessage = data?.getStringExtra(ERROR_KEY)
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitButton.setOnClickListener {
            val colorCode = findViewById<TextInputEditText>(R.id.color_code_input_field)
                .text.toString()

            if (colorCode.isNotEmpty()) {
                if (colorCode.length < 6) {
                    Toast.makeText(this, getString(R.string.color_code_input_wrong_length), Toast.LENGTH_LONG).show()
                } else {
                    val resultIntent = Intent(this, ResultActivity::class.java)
                    resultIntent.putExtra(COLOR_KEY, colorCode)
                    resultLauncher.launch(resultIntent)
                }
            } else {
                Toast.makeText(this, getString(R.string.color_code_input_empty), Toast.LENGTH_LONG).show()
            }
        }
    }
}