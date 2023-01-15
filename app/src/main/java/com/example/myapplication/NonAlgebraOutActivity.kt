package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NonAlgebraOutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_non_algebra_out)

        val intent = intent
        val input = intent.getStringExtra("input").toString()
        val output = intent.getStringExtra("output").toString()

        findViewById<TextView>(R.id.textInput).text = input
        findViewById<TextView>(R.id.textOutput).text = output

        findViewById<Button>(R.id.backButton).setOnClickListener{
            val mainIntent = Intent(this@NonAlgebraOutActivity,MainActivity::class.java)
            startActivity(mainIntent)
        }
    }
}