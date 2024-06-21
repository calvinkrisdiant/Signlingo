package com.capstone.signlingo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, OnboardingActivity::class.java))
//            startActivity(Intent(this, MainActivity::class.java))

            finish()
        }, 3000) // 3000 ms = 3 detik
    }
}
