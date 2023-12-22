package com.example.loginapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.FileOutputStream

class loggedin : AppCompatActivity() {

    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loggedin)

        sharedPref = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        val img : String? = sharedPref.getString("KEY_IMG_PATH", "")
        val UsernamePref : String? = sharedPref.getString("USERNAME", "")
        val Username : TextView = findViewById(R.id.UsernameWelcome)
        val Welcometext : String = "Hey $UsernamePref, is that you?"

        Username.setText(Welcometext)

        val profilepic : CircleImageView = findViewById(R.id.Profile_Pic)
        loadImageIntoImageView(img, profilepic)

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
    private fun loadImageIntoImageView(imagePath: String?, imageView: ImageView) {
        if (imagePath != null) {
            // Use BitmapFactory to decode the image file
            val bitmap = BitmapFactory.decodeFile(imagePath)

            // Check if the decoding was successful
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
            } else {
                // You might want to set a placeholder image or show an error message
                imageView.setImageResource(R.drawable.placeholder_image)
            }
        }
    }
}