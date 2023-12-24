package com.example.loginapp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SecondActivity : AppCompatActivity() {
    lateinit var Addbtn: ImageButton
    lateinit var fname: TextView
    lateinit var ProfilePic: ImageView
    lateinit var btnnav: BottomNavigationView
    lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        btnnav = findViewById(com.example.loginapp.R.id.bottomNavigationView)
        btnnav.background = null
        val source = intent.getStringExtra("source")
        val sharedPref = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)



        val editor = sharedPref.edit()

        if ("LoginActivity" == source) {
            fab = findViewById(R.id.fab)
            fab.setOnClickListener {
                startActivity(Intent(this, AddNote::class.java))
            }

            btnnav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.miProfile -> {
                        startActivity(Intent(applicationContext, UserProfile::class.java))
                        overridePendingTransition(0, 0)
                        return@setOnItemSelectedListener true
                    }

                    R.id.miHome -> {
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }

            btnnav.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.miProfile -> {
                        val ProfileIntent = Intent(this, UserProfile::class.java)
                        startActivity(ProfileIntent)
                        true
                    }

                    else -> false
                }
            }
            fname = findViewById(R.id.firstname_welcome)
            ProfilePic = findViewById(R.id.Profile_Pic)


            val receiverIntent = intent // to get existing intent activity
            var receivedFname: String? = receiverIntent.getStringExtra("KEY_FNAME")
            var receivedLname: String? = receiverIntent.getStringExtra("KEY_LNAME")
            var receivedImg: String? = receiverIntent.getStringExtra("KEY_IMG_PATH")
            editor.putString("FIRST_NAME", receivedFname)
            editor.putString("KEY_IMG_PATH", receivedImg)
            editor.putString("LAST_NAME", receivedLname)
            editor.apply()
            receivedFname += ","
            fname.setText(receivedFname)

            loadImageIntoImageView(receivedImg, ProfilePic)

        }
        else if("ProfileActivity" == source){
            fname = findViewById(R.id.firstname_welcome)
            ProfilePic = findViewById(R.id.Profile_Pic)

            var receivedFname : String? = sharedPref.getString("FIRST_NAME", "")
            var receivedImg : String? = sharedPref.getString("KEY_IMG_PATH", "")
            receivedFname += ","

            fname.setText(receivedFname)
            loadImageIntoImageView(receivedImg, ProfilePic)

            fab = findViewById(R.id.fab)
            fab.setOnClickListener {
                btnnav.selectedItemId = R.id.placeholder
                startActivity(Intent(this, AddNote::class.java))
            }

            btnnav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.miProfile -> {
                        startActivity(Intent(applicationContext, UserProfile::class.java))
                        overridePendingTransition(0, 0)
                        return@setOnItemSelectedListener true
                    }

                    R.id.miHome -> {
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
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
}