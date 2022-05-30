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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        val  signinbutton2 = findViewById<Button>(R.id.signin)
        val  register = findViewById<Button>(R.id.regbtn)
        val txtFirstName=findViewById<EditText>(R.id.firstName)
        val txtLastName=findViewById<EditText>(R.id.lastName)
        val txtEmail=findViewById<EditText>(R.id.email)
        val txtUserName=findViewById<EditText>(R.id.username)
        val txtAge=findViewById<EditText>(R.id.age)
        val rgGender=findViewById<RadioGroup>(R.id.rgGender)
        var valGender: String? =null
        rgGender.setOnCheckedChangeListener{rgGender, i ->
            var rb=findViewById<RadioButton>(i)
            if (rb!=null){
                valGender=rb.hint.toString()
                Log.i("Gender", "${txtEmail.text} gender")
            }
        }
        val txtPassword=findViewById<EditText>(R.id.password)
        var txtConfirmpassword=findViewById<EditText>(R.id.confirmpassword)

        //Registeration button
        register.setOnClickListener{
            if(!(txtFirstName.text.isNullOrEmpty()&&txtLastName.text.isNullOrEmpty()&&txtEmail.text
                    .isNullOrEmpty()&&txtUserName.text.isNullOrEmpty())){
                val user=User(
                    0,
                    txtUserName.text.toString(),
                    txtFirstName.text.toString(),
                    txtLastName.text.toString(),
                    valGender.toString(),
                    txtAge.text.toString().toInt(),
                )
                val account=Account(
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
                    this.cancel()
                }
            }
            val intent = Intent(this,login::class.java)
            startActivity(intent)
            finish()

        }

        signinbutton2.setOnClickListener {
            val intent = Intent(this,login::class.java)
            startActivity(intent)
            finish()
        }
    }
}