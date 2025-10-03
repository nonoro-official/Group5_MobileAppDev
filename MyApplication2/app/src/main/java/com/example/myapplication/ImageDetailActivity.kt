package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

class ImageDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        val toolbar = findViewById<Toolbar>(R.id.detail_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Pet Details" // optional

        val detailImage = findViewById<ImageView>(R.id.detail_image)
        val detailName = findViewById<TextView>(R.id.detail_name)
        val detailAge = findViewById<TextView>(R.id.detail_age)
        val detailDesc = findViewById<TextView>(R.id.detail_desc)

        val name = intent.getStringExtra("name")
        val age = intent.getStringExtra("age")
        val desc = intent.getStringExtra("desc")
        val imageResId = intent.getIntExtra("image", 0)

        detailName.text = name
        detailAge.text = "Age: $age"
        detailDesc.text = desc
        detailImage.setImageResource(imageResId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
