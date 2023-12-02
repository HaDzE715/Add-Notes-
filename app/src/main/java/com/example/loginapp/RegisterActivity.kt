package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.loginapp.NetworkService.apiService
import com.example.loginapp.NetworkService.registerUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val ArrowBack : ImageView = findViewById(R.id.arrow_back)
        val RegisterButton : Button = findViewById(R.id.RegForm_btn)
        val firstname : EditText = findViewById(R.id.firstName_input)
        val lastname : EditText = findViewById(R.id.lastName_input)
        val uname : EditText = findViewById(R.id.username_input)
        val pwd : EditText = findViewById(R.id.Registerpassword_input)

        ArrowBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        RegisterButton.setOnClickListener {
            val fname = firstname.text.toString()
            val lname = lastname.text.toString()
            val username = uname.text.toString()
            val password = pwd.text.toString()
            val img = "NONE"

            val registrationData = RegistrationData(fname, lname, username, password, img)

            // Call the registerUser function
            registerUser(registrationData) { success, message ->
                if (success) {
                    Log.e("Registration", "Success!")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // Close the registration page
                } else {
                    // Registration failed, show an error message
                    Log.e("Registration", "Failed!")
                }
            }
        }
        }
    }
