package com.example.lab_week_02_b

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class ResultActivity : AppCompatActivity() {
    companion object {
        const val COLOR_KEY = "COLOR_KEY"
        const val ERROR_KEY = "ERROR_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        handleIntent(intent) // pertama kali activity dibuka
        findViewById<Button>(R.id.back_button).setOnClickListener {
            finish()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)        // update intent lama ke intent baru
        handleIntent(intent)     // handle ulang input terbaru
    }

    private fun handleIntent(intent: Intent) {
        val colorCode = intent.getStringExtra(COLOR_KEY)

        try {
            val backgroundScreen = findViewById<ConstraintLayout>(R.id.background_screen)
            backgroundScreen.setBackgroundColor(Color.parseColor("#$colorCode"))

            val resultMessage = findViewById<TextView>(R.id.color_code_result_message)
            resultMessage.text = getString(R.string.color_code_result_message, colorCode?.uppercase())
        } catch (e: IllegalArgumentException) {
            // Kirim error balik ke MainActivity
            val data = Intent().apply {
                putExtra(ERROR_KEY, getString(R.string.color_code_input_invalid))
            }
            setResult(Activity.RESULT_CANCELED, data)
            finish()
        }
    }
}