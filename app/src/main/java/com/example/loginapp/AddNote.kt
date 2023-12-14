package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


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

            val resultIntent = Intent()
            resultIntent.putExtra("key", noteContent)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}

