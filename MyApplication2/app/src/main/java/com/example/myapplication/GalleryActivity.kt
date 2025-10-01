package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gallery)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val petTitles = arrayOf("Cat on the Rail", "High on Catnip", "Do You Have Games On Your Phone Vibe",
            "Sleepy Sausage" /*, "Pet 5", "Pet 6"*/)
        val petImages = intArrayOf(R.drawable.ming, R.drawable.dandan, R.drawable.ryan, R.drawable.maddison)

    }
}