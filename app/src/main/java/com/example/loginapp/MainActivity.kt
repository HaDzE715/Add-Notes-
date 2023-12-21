package com.example.loginapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream

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
        val sharedPref = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

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
                val decodedImg : ByteArray = decodeBase64(responseBodyimg)

                editor.putString("USERNAME", username)
                editor.putString("PASSWORD", password)
                editor.apply()

                if(responseBodyerror.isNullOrEmpty()){
                    val localImagePath = saveImageLocally(responseBodyimg)

                    val senderIntent = Intent(this, SecondActivity::class.java)
                    senderIntent.putExtra("KEY_FNAME", responseBodyfname.toString())
                    senderIntent.putExtra("KEY_LNAME", responseBodylname.toString())
                    senderIntent.putExtra("KEY_IMG_PATH", localImagePath)
                    senderIntent.putExtra("source", "LoginActivity")
                    startActivity(senderIntent)
                    finish()
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
    fun decodeBase64(encodedString: String?): ByteArray {
        return Base64.decode(encodedString, Base64.DEFAULT)
    }

    private fun saveImageLocally(base64String: String?): String {
        // Decode Base64 to byte array
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)

        // Save the byte array as a file
        val fileName = "profile_image.jpg"
        val file = File(filesDir, fileName)
        FileOutputStream(file).use { outputStream ->
            outputStream.write(decodedBytes)
        }

        return file.absolutePath
    }
}