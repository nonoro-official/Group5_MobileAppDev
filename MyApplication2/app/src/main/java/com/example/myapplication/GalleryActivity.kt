package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.GridView
import android.content.Intent
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ToggleButton
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
        val toggleDarkMode = findViewById<ToggleButton>(R.id.toggleDarkMode)

        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        toggleDarkMode.isChecked = (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES)

        toggleDarkMode.setOnCheckedChangeListener { _, isChecked ->
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

        // redirect to full image + details
        gridView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ImageDetailActivity::class.java)
            intent.putExtra("title", petTitles[position])
            intent.putExtra("image", petImages[position])
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


            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

    }
}