package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class SecondActivity : AppCompatActivity()
{
    lateinit var Addbtn : ImageButton
    lateinit var fname : TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        fname = findViewById(R.id.firstname_welcome)

        val receiverIntent = intent // to get existing intent activity
        var receivedFname : String? = receiverIntent.getStringExtra("KEY_FNAME")
        var receivedLname : String? = receiverIntent.getStringExtra("KEY_LNAME")
        var receivedImg : String? = receiverIntent.getStringExtra("KEY_IMG")
        receivedFname+= ","
        fname.setText(receivedFname)


        Addbtn = findViewById(R.id.AddNote)

        Addbtn.setOnClickListener {
            startActivity(Intent(this@SecondActivity, AddNote::class.java))
        }
    }
}