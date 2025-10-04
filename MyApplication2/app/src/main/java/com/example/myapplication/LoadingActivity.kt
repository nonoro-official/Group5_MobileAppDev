package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar

class LoadingActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val nextActivity = intent.getStringExtra("nextActivity")
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        Handler(Looper.getMainLooper()).postDelayed({
            when (nextActivity) {
                "Login" -> startActivity(Intent(this, LoginActivity::class.java))
                "Gallery" -> startActivity(Intent(this, GalleryActivity::class.java))
            }
            finish()
        }, 2000)
    }
}
