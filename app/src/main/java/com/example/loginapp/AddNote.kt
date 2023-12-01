package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class AddNote : AppCompatActivity() {
    lateinit var addNote: Button
    lateinit var note: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        addNote = findViewById(R.id.AddNote)
        note = findViewById(R.id.noteInput)

        addNote.setOnClickListener {
            val noteContent = note.text.toString()
            Toast.makeText(this, "Note added successfully!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SecondActivity::class.java))

        }
    }
}

