package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.login.Data.Entities.Account
import com.example.login.Data.Entities.User
import com.example.login.Data.GGDbContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        val  signinbutton2 = findViewById<Button>(R.id.signin)
        val  register = findViewById<Button>(R.id.regbtn)
        var txtFirstName=findViewById<EditText>(R.id.firstName)
        var txtLastName=findViewById<EditText>(R.id.lastName)
        var txtEmail=findViewById<EditText>(R.id.email)
        var txtUserName=findViewById<EditText>(R.id.username)
        var txtAge=findViewById<EditText>(R.id.age)
        var rgGender=findViewById<RadioGroup>(R.id.rgGender)
        var valGender: String? =null
        rgGender.setOnCheckedChangeListener{rgGender, i ->
            var rb=findViewById<RadioButton>(i)
            if (rb!=null){
                valGender=rb.hint.toString()
                Log.i("Gender", "${txtEmail.text} gender")
            }
        }
        var txtPassword=findViewById<EditText>(R.id.password)
        var txtConfirmpassword=findViewById<EditText>(R.id.confirmpassword)

        register.setOnClickListener{
            var user=User(
                0,
                txtUserName.text.toString(),
                txtFirstName.text.toString(),
                txtLastName.text.toString(),
                valGender.toString(),
                txtAge.text.toString().toInt(),
            )
            var account=Account(
                txtUserName.text.toString(),
                txtEmail.text.toString(),
                txtPassword.text.toString(),
                "profiledefault",
                "coverdefault",
            )

            val context=this
            CoroutineScope(Dispatchers.IO).launch {
                val Dao = GGDbContext.getInstance(context).gGdbDao
                Dao.NewAccount(account)
                Dao.NewUser(user)
            }
        }

        signinbutton2.setOnClickListener {
            val intent = Intent(this,login::class.java)
            startActivity(intent)
            finish()
        }
    }
}