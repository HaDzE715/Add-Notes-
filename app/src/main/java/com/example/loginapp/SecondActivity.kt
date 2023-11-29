package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity



class SecondActivity : AppCompatActivity()
{
    lateinit var Addbtn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Addbtn = findViewById(R.id.AddNote)

        Addbtn.setOnClickListener {
            startActivity(Intent(this@SecondActivity, AddNote::class.java))
        }
    }
}