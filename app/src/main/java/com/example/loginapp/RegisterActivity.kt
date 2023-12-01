package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val ArrowBack : ImageView = findViewById(R.id.arrow_back)
        val RegisterButton : Button = findViewById(R.id.RegForm_btn)

        ArrowBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        RegisterButton.setOnClickListener {
            Toast.makeText(this, "Account Registered Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}