package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.log

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val loginBtn = findViewById<ImageButton>(R.id.login_btn)

        loginBtn.setOnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            intent.putExtra("nextActivity", "Login")
            startActivity(intent)
        }
    }
}
