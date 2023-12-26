package com.example.loginapp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SecondActivity : AppCompatActivity() {
    lateinit var Addbtn: ImageButton
    lateinit var fname: TextView
    lateinit var ProfilePic: ImageView
    lateinit var btnnav: BottomNavigationView
    lateinit var fab: FloatingActionButton
    lateinit var lastlogin: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        btnnav = findViewById(com.example.loginapp.R.id.bottomNavigationView)
        btnnav.background = null
        val source = intent.getStringExtra("source")
        val sharedPref = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
        val notesContainer = findViewById<LinearLayout>(R.id.notesContainer)
        val notes = getNotesProperty()

        val editor = sharedPref.edit()

        if ("LoginActivity" == source) {
            displayNotes(notesContainer)
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
            lastlogin = findViewById(R.id.last_login)


            val receiverIntent = intent // to get existing intent activity
            var receivedFname: String? = receiverIntent.getStringExtra("KEY_FNAME")
            var receivedLname: String? = receiverIntent.getStringExtra("KEY_LNAME")
            var receivedImg: String? = receiverIntent.getStringExtra("KEY_IMG_PATH")
            var receivedLastLogin: String? = receiverIntent.getStringExtra("KEY_LAST_LOGIN")

            editor.putString("FIRST_NAME", receivedFname)
            editor.putString("KEY_IMG_PATH", receivedImg)
            editor.putString("LAST_NAME", receivedLname)
            editor.putString("KEY_LAST_LOGIN", receivedLastLogin)
            editor.apply()
            receivedFname += ","
            fname.setText(receivedFname)
            lastlogin.setText(receivedLastLogin)

            loadImageIntoImageView(receivedImg, ProfilePic)

        }
        else if("ProfileActivity" == source){
            displayNotes(notesContainer)
            fname = findViewById(R.id.firstname_welcome)
            ProfilePic = findViewById(R.id.Profile_Pic)
            lastlogin = findViewById(R.id.last_login)

            var receivedFname : String? = sharedPref.getString("FIRST_NAME", "")
            var receivedImg : String? = sharedPref.getString("KEY_IMG_PATH", "")
            var receivedLastLogin : String? = sharedPref.getString("KEY_LAST_LOGIN", "")
            receivedFname += ","
            receivedLastLogin = "Last logged in $receivedLastLogin."
            if (receivedLastLogin != null) {
                Log.e("LASTLGN", receivedLastLogin)
            }
            fname.setText(receivedFname)
            lastlogin.setText(receivedLastLogin)
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
    private fun getNotesProperty(): List<String> {
        val sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
        val notesJson = sharedPreferences.getString("notes", "[]")

        // Convert the JSON string to a list of notes
        return Gson().fromJson(notesJson, object : TypeToken<List<String>>() {}.type) ?: emptyList()
    }

    private fun displayNotes(notesContainer: LinearLayout) {
        val notes = getNotesProperty()

        for (note in notes) {
            val noteTextView = TextView(this)

            // Create new LayoutParams
            val layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            noteTextView.layoutParams = layoutParams
            noteTextView.text = note
            noteTextView.textSize = 18f
            noteTextView.setTextColor(Color.WHITE)
            noteTextView.setTypeface(Typeface.DEFAULT_BOLD)
            noteTextView.typeface = Typeface.create("@font/ageo", Typeface.NORMAL)

            val marginLayoutParams = noteTextView.layoutParams as LinearLayout.LayoutParams
            marginLayoutParams.setMargins(70, 10, 0, 0)

            notesContainer.addView(noteTextView)
        }
    }
}