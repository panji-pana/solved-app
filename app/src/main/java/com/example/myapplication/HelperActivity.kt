package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class HelperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helper)

        findViewById<ImageButton>(R.id.buttonMainBack).setOnClickListener{
            val intent = Intent(this@HelperActivity,MainActivity::class.java)
            startActivity(intent)
        }
    }
}