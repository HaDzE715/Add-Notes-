package com.example.loginapp

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.util.Base64
import android.widget.ImageView


class SecondActivity : AppCompatActivity()
{
    lateinit var Addbtn : ImageButton
    lateinit var fname : TextView
    lateinit var ProfilePic : ImageView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        fname = findViewById(R.id.firstname_welcome)
        ProfilePic = findViewById(R.id.Profile_Pic)


        val receiverIntent = intent // to get existing intent activity
        var receivedFname : String? = receiverIntent.getStringExtra("KEY_FNAME")
        var receivedLname : String? = receiverIntent.getStringExtra("KEY_LNAME")
        var receivedImg : String? = receiverIntent.getStringExtra("KEY_IMG_PATH")
        receivedFname+= ","
        fname.setText(receivedFname)

        loadImageIntoImageView(receivedImg, ProfilePic)


        Addbtn = findViewById(R.id.AddNote)

        Addbtn.setOnClickListener {
            startActivity(Intent(this@SecondActivity, AddNote::class.java))
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
                // Handle the case where decoding fails
                // You might want to set a placeholder image or show an error message
                imageView.setImageResource(R.drawable.placeholder_image)
            }
        }
    }
}