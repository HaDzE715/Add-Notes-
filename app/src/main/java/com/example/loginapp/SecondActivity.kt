package com.example.loginapp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response


class SecondActivity : AppCompatActivity() {
    lateinit var Addbtn: ImageButton
    lateinit var fname: TextView
    lateinit var ProfilePic: ImageView
    lateinit var btnnav: BottomNavigationView
    lateinit var fab: FloatingActionButton
    lateinit var lastlogin: TextView
    lateinit var LgoutBtn: ImageView
    lateinit var username: String
    lateinit var textViewNotes: TextView


    fun loadImageIntoImageView(imagePath: String?, imageView: ImageView) {
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
    private fun fetchNotesAndUpdateUI(username: String) {
        fetchNotes(username) { notes, error ->
            runOnUiThread {
                if (notes != null) {
                    Log.e("NOTES", "WORKD")

                    val listView: ListView = findViewById(R.id.listViewNotes)
                    val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notes)
                    listView.adapter = adapter
                } else {
                    Log.e("NOTES", "DIDNTWORK!")
                }
            }
        }
    }


    //    private fun fetchNotesAndUpdateUI(username: String){
//        fetchNotes(username){notes, error->
//            runOnUiThread {
//                if(notes != null){
//                    Log.e("NOTES", "WORKD")
//                    textViewNotes.text = notes.joinToString("\n\n")
//                } else{
//                    Log.e("NOTES", "DIDNTWORK!")
//                }
//            }
//
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        btnnav = findViewById(com.example.loginapp.R.id.bottomNavigationView)
        LgoutBtn = findViewById(R.id.LogoutBtn)
        btnnav.background = null
        val source = intent.getStringExtra("source")
        val sharedPref = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        username = sharedPref.getString("USERNAME", "").toString()

        if ("LoginActivity" == source) {
            fab = findViewById(R.id.fab)
            fab.setOnClickListener {
                startActivity(Intent(this, AddNote::class.java))
            }
            fetchNotesAndUpdateUI(username)

            LgoutBtn.setOnClickListener {
                // Clear user credentials
                try {
                    // Clear user credentials
                    editor.remove("USERNAME")
                    editor.remove("PASSWORD")
                    editor.apply()
                    // Start the MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Optional: finish the current activity
                } catch (e: Exception) {
                    Log.e("Logout", "Error during logout: ${e.message}", e)
                    // Handle the exception as needed
                }
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

        } else if ("ProfileActivity" == source) {
            fname = findViewById(R.id.firstname_welcome)
            ProfilePic = findViewById(R.id.Profile_Pic)
            lastlogin = findViewById(R.id.last_login)

            var receivedFname: String? = sharedPref.getString("FIRST_NAME", "")
            var receivedImg: String? = sharedPref.getString("KEY_IMG_PATH", "")
            var receivedLastLogin: String? = sharedPref.getString("KEY_LAST_LOGIN", "")
            receivedFname += ","
            receivedLastLogin = "Last logged in $receivedLastLogin."

            fname.setText(receivedFname)
            lastlogin.setText(receivedLastLogin)
            loadImageIntoImageView(receivedImg, ProfilePic)

            fetchNotesAndUpdateUI(username)
        }

        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            btnnav.selectedItemId = R.id.placeholder
            startActivity(Intent(this, AddNote::class.java))
        }

        LgoutBtn.setOnClickListener {
            // Clear user credentials
            try {
                // Clear user credentials
                editor.remove("USERNAME")
                editor.remove("PASSWORD")
                editor.apply()
                // Start the MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Optional: finish the current activity
            } catch (e: Exception) {
                Log.e("Logout", "Error during logout: ${e.message}", e)
                // Handle the exception as needed
            }
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