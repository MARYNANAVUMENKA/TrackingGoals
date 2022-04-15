package com.example.trackinggoals

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trackinggoals.model.Repositories

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}