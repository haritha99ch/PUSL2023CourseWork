package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val  loginbutton = findViewById<Button>(R.id.signinbtn)

        loginbutton.setOnClickListener {
            val intent = Intent(this,gamefeed::class.java)
            startActivity(intent)
            finish()
        }



        val regbtn = findViewById<Button>(R.id.singupbtn)

        regbtn.setOnClickListener{

            val intent = Intent(this,Registration::class.java)
            startActivity(intent)
            finish()
        }

    }
}