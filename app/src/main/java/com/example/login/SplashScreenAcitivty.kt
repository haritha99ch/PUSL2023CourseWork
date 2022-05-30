package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SplashScreenAcitivty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen_acitivty)
        val splash = findViewById<ImageView>(R.id.splash_icon)

        splash.alpha = 0f
        splash.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this, MainActivity::class.java)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(i)
            finish()
        }

    }
}