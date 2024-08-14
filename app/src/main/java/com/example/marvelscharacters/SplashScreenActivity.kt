package com.example.marvelscharacters

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        enableEdgeToEdge()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, splashScreen::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}