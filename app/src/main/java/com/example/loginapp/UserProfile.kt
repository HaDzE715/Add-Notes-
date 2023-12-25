package com.example.loginapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.InputType
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class UserProfile : AppCompatActivity() {

    lateinit var btnnav : BottomNavigationView
    var selectedImageBase64: String? = null
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPref: SharedPreferences
    var imgPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        sharedPref = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        val FirstName = sharedPref.getString("FIRST_NAME", "N")
        val Username : String? = sharedPref.getString("USERNAME", "")
        val Password = sharedPref.getString("PASSWORD", "")
        val LastName = sharedPref.getString("LAST_NAME", "")
        var ProfilePic : ImageView = findViewById(R.id.Profile_Pic)
        var UsernameProfile : TextView = findViewById(R.id.UsernameProfile)
        var FirstnameProfile : TextView = findViewById(R.id.FirstNameProfile)
        var LastnameProfile : TextView = findViewById(R.id.LastNameProfile)
        var PasswordProfile : EditText = findViewById<EditText>(R.id.PasswordProfile)
        val ShowPassword : ImageView = findViewById(R.id.ShowPassword)
        var Savechanges : Button = findViewById(R.id.SavechangesButton)
        var InfoBtn : ImageView = findViewById(R.id.InfoBtn)
        var userData : updateData?


        imgPath = sharedPref.getString("KEY_IMG_PATH", "")

        PasswordProfile.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        PasswordProfile.setText(Password)
        UsernameProfile.setText(Username)
        FirstnameProfile.setText(FirstName)
        LastnameProfile.setText(LastName)

        loadImageIntoImageView(imgPath, ProfilePic)

        UsernameProfile.isEnabled = false

        btnnav = findViewById(R.id.bottomNavigationView)
        btnnav.selectedItemId = R.id.miProfile

        ShowPassword.setOnClickListener {// Eye on show password
            PasswordProfile.inputType = InputType.TYPE_CLASS_TEXT
        }
        ProfilePic.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }
        InfoBtn.setOnClickListener {
            Toast.makeText(this, "Username cannot be changed!", Toast.LENGTH_SHORT).show()
        }
        Savechanges.setOnClickListener {
            val HomeIntent = Intent(this, SecondActivity::class.java)
            val fname : String = FirstnameProfile.text.toString()
            val lname : String = LastnameProfile.text.toString()
            val pass : String = PasswordProfile.text.toString()
            var OldImage : String? = sharedPref.getString("OLDIMAGE", "")

            if(selectedImageBase64 != null) {
                userData = Username?.let { it1 -> updateData(fname, lname, it1, pass,
                    selectedImageBase64!!
                ) }
            }
            else
            {
                userData = Username?.let { it1 -> updateData(fname, lname, it1, pass, OldImage!!) }
            }
            if(selectedImageBase64 != null)
            {
                val NewLocalImgPath = saveImageLocally(selectedImageBase64)
                Log.e("LOCAL", NewLocalImgPath)
                editor.putString("KEY_IMG_PATH", NewLocalImgPath)
                editor.apply()
            }
            HomeIntent.putExtra("source", "ProfileActivity")
            editor.putString("FIRST_NAME", fname)
            editor.putString("LAST_NAME", lname)
            editor.apply()
            if (userData != null) {
                updateUserDetails(userData!!) { success, _ ->
                    if (success) {
                        Log.e("UpdatingServer", "SUCESS!")
                    } else {
                        Log.e("UpdatingServer", "FAILED!")
                    }
                }
            }
            Toast.makeText(this, "Changed successfully", Toast.LENGTH_SHORT).show()
            startActivity(HomeIntent)
        }
        btnnav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.miHome -> {
                    val HomeIntent = Intent(this, SecondActivity::class.java)
                    HomeIntent.putExtra("source", "ProfileActivity")
                    HomeIntent.putExtra("KEY_FNAME", FirstName)
                    HomeIntent.putExtra("KEY_IMG_PATH", imgPath)
                    startActivity(HomeIntent)
                    overridePendingTransition(0, 0)
                    return@setOnItemSelectedListener true
                }
                R.id.miProfile -> {
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            btnnav.selectedItemId = R.id.placeholder
            startActivity(Intent(this, AddNote::class.java))
        }
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

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            // Handle the selected image
            if (result != null) {
                try {
                    val inputStream = contentResolver.openInputStream(result)
                    val selectedImageBitmap = BitmapFactory.decodeStream(inputStream)
                    val imageView: CircleImageView = findViewById(R.id.Profile_Pic)
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
    private fun saveImageLocally(base64String: String?): String {
        if (base64String.isNullOrEmpty())
            return ""

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