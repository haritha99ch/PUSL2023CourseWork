package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.login.Data.Entities.Relations.UserAccount
import com.example.login.Data.Entities.User
import com.example.login.Data.GGDbContext
import com.example.login.Session.LoginPref
import kotlinx.coroutines.*

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginbutton: Button = findViewById(R.id.signinbtn)
        val txtUserName = findViewById<EditText>(R.id.loguserName)
        val txtPassword = findViewById<EditText>(R.id.logpassword)
        var session = LoginPref(this)
        if (session.isLoggedIn()) {
            val intent = Intent(this, gamefeed::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        loginbutton.setOnClickListener {
            var userName = txtUserName.text.toString().trim()
            var password = txtPassword.text.toString()
            var userAccount:UserAccount?
            var isValid=false
            if (!userName.isNullOrEmpty() && !password.isNullOrEmpty()) {
                CoroutineScope(Dispatchers.IO).launch{
                    val Dao = GGDbContext.getInstance(applicationContext).gGdbDao
                    userAccount = Dao.selectUserAccount(userName)
                    isValid = userAccount != null
                    if (userAccount==null){
                        withContext(Dispatchers.Main){
                            Toast.makeText(applicationContext, "Invalid Login", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        withContext(Dispatchers.Main){
                            session.createLoginSession(userName)
                            var intent = Intent(applicationContext, gamefeed::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    Log.i("Login", "$userAccount")
                    Log.i("Login", "$isValid")
                    this.cancel()
                }
                Log.i("Login", "$isValid")

            } else {
                Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show()
            }
        }

        val signupbutton: Button = findViewById(R.id.signupbtn)

        signupbutton.setOnClickListener {

            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
            finish()
        }

    }

}