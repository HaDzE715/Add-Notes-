package com.example.loginapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddNote : AppCompatActivity() {
    lateinit var addNote: Button
    lateinit var note: EditText
    lateinit var subject : EditText
    lateinit var btnnav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        val sharedPref = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)

        addNote = findViewById(R.id.AddNote)
        note = findViewById(R.id.noteInput)
        subject = findViewById(R.id.SubjectnoteInput)
        btnnav = findViewById(R.id.bottomNavigationView)
        btnnav.selectedItemId = R.id.placeholder


        addNote.setOnClickListener {
            val noteContent = note.text.toString()
            val subjectContent = subject.text.toString()
            val Username = sharedPref.getString("USERNAME", "")
            val note = Username?.let { it1 -> Note(it1, noteContent) }

            if(noteContent.isNullOrEmpty() || subjectContent.isNullOrEmpty()) {
                Toast.makeText(this, "You didn't type a note or subject!", Toast.LENGTH_SHORT)
                    .show()
            }
            else{
                addNote(note!!) { success, message ->
                    if (success) {
                        Log.e("NOTE", "SUCCESS!")
                    } else {
                        Log.e("NOTE", "FAILED!")
                    }
                }
                Toast.makeText(this, "Note added successfully!", Toast.LENGTH_SHORT).show()
                val HomeIntent = Intent(this, SecondActivity::class.java)
                HomeIntent.putExtra("source", "ProfileActivity")
                startActivity(HomeIntent)
            }

        }
        btnnav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.miHome -> {
                    val HomeIntent = Intent(this, SecondActivity::class.java)
                    HomeIntent.putExtra("source", "ProfileActivity")
                    startActivity(HomeIntent)
                }

                R.id.miProfile -> {
                    startActivity(Intent(this, UserProfile::class.java))
                    overridePendingTransition(0, 0)
                }
            }
            true
        }
    }
}

