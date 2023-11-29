package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class MainActivity : AppCompatActivity() {

    lateinit var usernameInput : EditText
    lateinit var passwordInput : EditText
    lateinit var loginBtn : Button


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.login_btn)

        loginBtn.setOnClickListener {
//            val username = usernameInput.text.toString()
//            val password = passwordInput.text.toString()
//
//            if(username == "Hade Bayaa" && password == "Aa123456")
//            {
//                startActivity(Intent(this, SecondActivity::class.java))
//            }
        startActivity(Intent(this, SecondActivity::class.java))
    }
    }
}