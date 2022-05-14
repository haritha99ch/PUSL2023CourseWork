package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val  signinbutton2 = findViewById<Button>(R.id.signin)
        var txtEmail=findViewById<EditText>(R.id.email)
        var txtUserName=findViewById<EditText>(R.id.username)
        var txtAge=findViewById<EditText>(R.id.age)
        var rgGender=findViewById<RadioGroup>(R.id.rgGender)
        var valGender:String
        rgGender.setOnCheckedChangeListener{rgGender, i ->
            var rb=findViewById<RadioButton>(i)
            if (rb!=null){
                valGender=rb.hint.toString()
                Log.i("Gender", "$valGender gender")
            }
        }
        var txtPassword=findViewById<EditText>(R.id.password)
        var txtConfirmpassword=findViewById<EditText>(R.id.confirmpassword)

        signinbutton2.setOnClickListener {
            val intent = Intent(this,login::class.java)
            startActivity(intent)
            finish()
        }
    }
}