package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import android.view.View
import android.content.Intent

class MenuActivity : AppCompatActivity() {

    data class MenuItem(
        val id: Int,
        val name: String,
        val basePrice: Double,
        val imageResource: Int
    )

    private val menuItems = listOf(
        MenuItem(1, "Cappuccino", 120.00, R.drawable.coffee),
        MenuItem(2, "Juice", 85.00, R.drawable.juice),
        MenuItem(3, "Latte", 145.00, R.drawable.latte),
        MenuItem(4, "Milkshake", 135.00, R.drawable.milkshake),
        MenuItem(5, "Brownies", 80.00, R.drawable.brownies),
        MenuItem(6, "Cake", 150.00, R.drawable.cake),
        MenuItem(7, "Cookies", 75.00, R.drawable.cookie),
        MenuItem(8, "Croissant", 120.00, R.drawable.croissant)
    )

    private val sizeOptions = arrayOf("Small", "Medium", "Large")
    private val sizePrices = mapOf(
        "Small" to 0.0,
        "Medium" to 15.0,
        "Large" to 25.0,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = sharedPref.getString("username", "User")

        val greetingText = findViewById<TextView>(R.id.greeting_text)
        greetingText.text = "Hello, $username!"

        setupMenuItems()

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
        val viewCartBtn = findViewById<Button>(R.id.view_cart_btn)
        viewCartBtn.setOnClickListener {
            startActivity(Intent(this, CartViewerActivity::class.java))
        }
    }

    private fun setupMenuItems() {
        for(i in 1..menuItems.size) {
            setupMenuItem(i)
        }
    }

    private fun setupMenuItem(itemNumber: Int) {
        if(itemNumber > menuItems.size) return

        val menuItem = menuItems[itemNumber - 1]

        val itemName = findViewById<TextView>(resources.getIdentifier("item_name_$itemNumber", "id", packageName))
        val itemPrice = findViewById<TextView>(resources.getIdentifier("item_price_$itemNumber", "id", packageName))
        val sizeSpinner = findViewById<Spinner>(resources.getIdentifier("size_spinner_$itemNumber", "id", packageName))
        val addToCartBtn = findViewById<Button>(resources.getIdentifier("add_to_cart_$itemNumber", "id", packageName))

        itemName.text = menuItem.name
        updatePriceDisplay(itemPrice, menuItem.basePrice, "Medium")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sizeOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sizeSpinner.adapter = adapter

        sizeSpinner.setSelection(1)

        sizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedSize = sizeOptions[position]
                updatePriceDisplay(itemPrice, menuItem.basePrice, selectedSize)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        addToCartBtn.setOnClickListener {
            val selectedSize = sizeSpinner.selectedItem as String
            val finalPrice = menuItem.basePrice + (sizePrices[selectedSize] ?: 0.0)

            saveToCart(menuItem.name, selectedSize, finalPrice)

            Toast.makeText(
                this,
                "Added to cart: ${menuItem.name} ($selectedSize) - ₱${"%.2f".format(finalPrice)}",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun updatePriceDisplay(priceTextView: TextView, basePrice: Double, size: String) {
        val sizePrice = sizePrices[size] ?: 0.0
        val finalPrice = basePrice + sizePrice
        priceTextView.text = "₱${"%.2f".format(finalPrice)}"
    }

    private fun saveToCart(name: String, size: String, price: Double) {
        val cartItem = CartItem(name, size, price, 1)
        CartManager.addToCart(cartItem)
    }


}