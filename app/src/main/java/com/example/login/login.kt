package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.login.Session.LoginPref

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginbutton: Button = findViewById(R.id.signinbtn)
        val txtUserName=findViewById<EditText>(R.id.loguserName)
        val txtPassword=findViewById<EditText>(R.id.logpassword)
        var session=LoginPref(this)
        if (session.isLoggedIn()){
            val intent = Intent(this,gamefeed::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        loginbutton.setOnClickListener {
            var userName=txtUserName.text.toString().trim()
            var password=txtPassword.text.toString()
            if (!userName.isNullOrEmpty() && !password.isNullOrEmpty()){
                session.createLoginSession(userName)
                var intent=Intent(this,gamefeed::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show()
            }
        }

        val signupbutton: Button = findViewById(R.id.signupbtn)

        signupbutton.setOnClickListener {

            val intent = Intent(this,Registration::class.java)
            startActivity(intent)
            finish()
        }

    }

}