package com.example.loginapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class AddNote : AppCompatActivity() {
    lateinit var addNote: Button
    lateinit var note: EditText
    lateinit var subject : EditText
    lateinit var btnnav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        addNote = findViewById(R.id.AddNote)
        note = findViewById(R.id.noteInput)
        subject = findViewById(R.id.SubjectnoteInput)
        btnnav = findViewById(R.id.bottomNavigationView)
        btnnav.selectedItemId = R.id.placeholder


        addNote.setOnClickListener {
            val noteContent = note.text.toString()
            val subjectContent = subject.text.toString()

            if(noteContent.isNullOrEmpty() || subjectContent.isNullOrEmpty()) {
                Toast.makeText(this, "You didn't type a note or subject!", Toast.LENGTH_SHORT)
                    .show()
            }
            else{
                saveNoteToSharedPreferences(subjectContent, noteContent)
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
    private fun saveNoteToSharedPreferences(subject: String, content: String) {
        val sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)

        // Retrieve existing notes
        val existingNotes = getNotes(sharedPreferences)

        // Add the new note
        existingNotes.add("$subject: $content")

        // Save the updated notes list to SharedPreferences
        saveNotesToSharedPreferences(existingNotes, sharedPreferences)
        val updatedNotes = getNotes(sharedPreferences)
        Log.d("Notes", updatedNotes.toString())
    }

    private fun getNotes(sharedPreferences: SharedPreferences): MutableList<String> {
        val notesJson = sharedPreferences.getString("notes", "[]")

        // Convert the JSON string to a list of notes
        return Gson().fromJson(notesJson, object : TypeToken<MutableList<String>>() {}.type)
            ?: mutableListOf()
    }

    private fun saveNotesToSharedPreferences(notes: List<String>, sharedPreferences: SharedPreferences) {
        val notesJson = Gson().toJson(notes)

        // Save the JSON string to SharedPreferences
        sharedPreferences.edit().putString("notes", notesJson).apply()
    }
}

