package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class ImageDetailActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        // Setup Toolbar with back button
        val toolbar = findViewById<Toolbar>(R.id.detail_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Pet Details" // optional

        // Get the views from the XML
        val detailImage = findViewById<ImageView>(R.id.detail_image)
        val detailName = findViewById<TextView>(R.id.detail_name)
        val detailAge = findViewById<TextView>(R.id.detail_age)
        val detailDesc = findViewById<TextView>(R.id.detail_desc)

        // Get the data sent from GalleryActivity
        val name = intent.getStringExtra("name")
        val age = intent.getStringExtra("age")
        val desc = intent.getStringExtra("desc")
        val imageResId = intent.getIntExtra("image", 0)

        // Set the data into the views
        detailName.text = name
        detailAge.text = "Age: $age" // don't add "years" again
        detailDesc.text = desc
        detailImage.setImageResource(imageResId)

        // Seek Bar
        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        val petImages = intArrayOf(R.drawable.ming, R.drawable.dandan, R.drawable.ryan,
            R.drawable.maddison, R.drawable.kylo, R.drawable.bumju)
        val petNames = arrayOf("Ming", "Dandan", "Ryan", "Maddison", "Kylo", "Bumju")
        val petAges = arrayOf("5 years", "6 months", "6 months", "5 months", "5 years", "2 years")
        val petDescriptions = arrayOf(
            "A fat cat who loves rails.",
            "Always high on catnip.",
            "iPad kid kitty.",
            "The sausage is so soft and warm.",
            "Protective mama but super sweet.",
            "Lurks in the corner, watching everything."
        )

        seekBar.max = petNames.size - 1
        seekBar.progress = petNames.indexOf(name).takeIf { it >= 0 } ?: 0

        fun updatePet(index: Int) {
            detailName.text = petNames[index]
            detailAge.text = "Age: ${petAges[index]}"
            detailDesc.text = petDescriptions[index]
            detailImage.setImageResource(petImages[index])
        }

        // Initialize first pet
        updatePet(seekBar.progress)

        // Update when SeekBar changes
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) updatePet(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

    }

    // Handle back button press
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
