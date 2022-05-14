package com.example.login.Session

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.shapes.PathShape
import com.example.login.login

class LoginPref {
    lateinit var pref:SharedPreferences
    lateinit var editor:SharedPreferences.Editor
    lateinit var context:Context
    var privateMode:Int=0
    constructor(context: Context){
        this.context=context
        pref=context.getSharedPreferences(PREF_NAME, privateMode)
        editor=pref.edit()
    }
    companion object{
        val PREF_NAME="Login_Preference"
        val IS_LOGIN="isLoggedIn"
        val KEY_USERNAME="userName"
    }
    fun createLoginSession(userName:String){
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_USERNAME, userName)
        editor.commit()
    }

    fun checkLogin(){
        if(!this.isLoggedIn()){
            var i=Intent(context, login::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }

    fun getUserDetails():HashMap<String, String>{
        var user:Map<String,String> = HashMap()
        (user as HashMap).put(KEY_USERNAME, pref.getString(KEY_USERNAME, null)!!)
        return user
    }

    fun LogoutUser(){
        editor.clear()
        editor.commit()
        var i=Intent(context, login::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }

    fun isLoggedIn():Boolean{
        return pref.getBoolean(IS_LOGIN, false)
    }

}