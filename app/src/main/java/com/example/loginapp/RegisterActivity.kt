package com.example.loginapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.NetworkService.registerUser
import java.io.ByteArrayOutputStream
import java.io.IOException

class RegisterActivity : AppCompatActivity(), ImagePickerCallBack {

    // This property will hold the selected image Base64 string
    var selectedImageBase64: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val ArrowBack: ImageView = findViewById(R.id.arrow_back)
        val RegisterButton: Button = findViewById(R.id.RegForm_btn)
        val firstname: EditText = findViewById(R.id.firstName_input)
        val lastname: EditText = findViewById(R.id.lastName_input)
        val uname: EditText = findViewById(R.id.username_input)
        val pwd: EditText = findViewById(R.id.Registerpassword_input)
        val imageView: ImageView = findViewById(R.id.image_view)

        imageView.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        ArrowBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        RegisterButton.setOnClickListener {
            val fname = firstname.text.toString()
            val lname = lastname.text.toString()
            val username = uname.text.toString()
            val password = pwd.text.toString()

            // Use selectedImageBase64 directly
            val img = selectedImageBase64 ?: "NONE"

            val registrationData = RegistrationData(fname, lname, username, password, img)

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

    // This method is part of the ImagePickerCallBack interface
    override fun onImageSelected(base64String: String) {
        // This method is called when an image is selected
        selectedImageBase64 = base64String
    }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            // Handle the selected image
            if (result != null) {
                try {
                    val inputStream = contentResolver.openInputStream(result)
                    val selectedImageBitmap = BitmapFactory.decodeStream(inputStream)
                    val imageView: ImageView = findViewById(R.id.image_view)
                    imageView.setImageBitmap(selectedImageBitmap)
                    val imageBase64: String = convertBitmapToBase64(selectedImageBitmap)

                    // Use selectedImageBase64 directly
                    selectedImageBase64 = imageBase64

                    inputStream?.close() // Close the InputStream when done
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    fun convertBitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}