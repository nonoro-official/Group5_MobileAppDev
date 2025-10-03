package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.GridView
import android.content.Intent
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate

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

        // DARK THEME
        val switchDarkMode = findViewById<Switch>(R.id.switchDarkMode)

        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        switchDarkMode.isChecked = (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES)

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val petTitles = arrayOf("Cat on the Rail", "High on Catnip", "Do You Have Games On Your Phone Vibe",
            "Sleepy Sausage", "Happy Mama", "Floof in the Corner")
        val petImages = intArrayOf(R.drawable.ming, R.drawable.dandan, R.drawable.ryan,
            R.drawable.maddison, R.drawable.kylo, R.drawable.bumju)

        val gridView = findViewById<GridView>(R.id.gallery_view)
        val adapter = GridAdapter(this, petTitles, petImages)
        gridView.adapter = adapter

        // to full image + details
        gridView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ImageDetailActivity::class.java)
            intent.putExtra("title", petTitles[position])
            intent.putExtra("image", petImages[position])
            startActivity(intent)
        }

        // logout

    }
}