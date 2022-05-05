package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginbutton: Button = findViewById(R.id.signinbtn)

        loginbutton.setOnClickListener {
            val intent = Intent(this,gamefeed::class.java)
            startActivity(intent)
            finish()

        }

        val signupbutton: Button = findViewById(R.id.signupbtn)

        signupbutton.setOnClickListener {
            val intent = Intent(this,Registration::class.java)
            startActivity(intent)
            finish()
        }

    }

}