package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.loginapp.NetworkService.apiService
//import com.example.loginapp.NetworkService.performLogin


class MainActivity : AppCompatActivity() {

    lateinit var usernameInput : EditText
    lateinit var passwordInput : EditText
    lateinit var loginBtn : Button
    lateinit var registerBtn : Button


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameInput = findViewById(R.id.username_input)
        passwordInput = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.login_btn)
        registerBtn = findViewById(R.id.register_btn)

        loginBtn.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            val loginData = LoginData(username, password)


            performLogin(loginData){
                responseBody ->
                val responseBodyerror : String? = responseBody.error
                val responseBodyfname : String? = responseBody.fname
                val responseBodylname : String? = responseBody.lname
                val responseBodyimg : String? = responseBody.img

                if(responseBodyerror.isNullOrEmpty()){
                    val senderIntent = Intent(this, SecondActivity::class.java)
                    senderIntent.putExtra("KEY_FNAME", responseBodyfname.toString())
                    senderIntent.putExtra("KEY_LNAME", responseBodylname.toString())
                    senderIntent.putExtra("KEY_IMG", responseBodyimg.toString())
                    startActivity(senderIntent)
                }
                else{
                    Log.e("Logging in", "Failed!")
                }
            }
    }
        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}