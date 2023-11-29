package com.example.loginapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


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

        }
    }
}

