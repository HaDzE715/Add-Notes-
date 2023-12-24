package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class AddNote : AppCompatActivity() {
    lateinit var addNote: Button
    lateinit var note: EditText
    lateinit var btnnav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        addNote = findViewById(R.id.AddNote)
        note = findViewById(R.id.noteInput)
        btnnav = findViewById(R.id.bottomNavigationView)
        btnnav.selectedItemId = R.id.placeholder


        addNote.setOnClickListener {
            val noteContent = note.text.toString()
            Toast.makeText(this, "Note added successfully!", Toast.LENGTH_SHORT).show()

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
        btnnav.selectedItemId = 0
    }
}

