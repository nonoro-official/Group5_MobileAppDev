package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val act1Btn = findViewById<Button>(R.id.act1_btn)
        val act2Btn = findViewById<Button>(R.id.act2_btn)
        val act3Btn = findViewById<Button>(R.id.act3_btn)

        act1Btn.setOnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            intent.putExtra("nextActivity", "Sign Up")
            startActivity(intent)
        }

        act2Btn.setOnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            intent.putExtra("nextActivity", "Login")
            startActivity(intent)
        }

        act3Btn.setOnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            intent.putExtra("nextActivity", "Menu")
            startActivity(intent)
        }
    }
}
