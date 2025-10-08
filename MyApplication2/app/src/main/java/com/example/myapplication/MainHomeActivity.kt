package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val galleryBtn = findViewById<Button>(R.id.gallery_btn)
        val menuBtn = findViewById<Button>(R.id.menu_btn)

        galleryBtn.setOnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            intent.putExtra("nextActivity", "Gallery")
            startActivity(intent)
        }

        menuBtn.setOnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            intent.putExtra("nextActivity", "Menu")
            startActivity(intent)
        }

        // logout
        val logoutBtn = findViewById<ImageButton>(R.id.logout_btn)
        logoutBtn.setOnClickListener {
            Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show()

            val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val editor = sharedPref.edit()
            //editor.clear() (this is for when u want to fully clear out the user's data when logging out)
            editor.apply()


            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
