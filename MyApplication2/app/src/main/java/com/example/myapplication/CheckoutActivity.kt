package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CheckoutActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var totalPriceText: TextView
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        totalPriceText = findViewById(R.id.totalPriceText)
        confirmButton = findViewById(R.id.checkoutButton)

        // Get all items from the cart
        val cartItems = CartManager.getCartItems().toMutableList()

        // Set up adapter
        cartAdapter = CartAdapter(cartItems)
        recyclerView.adapter = cartAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Calculate total price
        val total = cartItems.sumOf { it.price * it.quantity }
        totalPriceText.text = "Total: ₱%.2f".format(total)

        // Confirm purchase
        confirmButton.setOnClickListener {
            Toast.makeText(this, "Payment Confirmed! Thank you!", Toast.LENGTH_LONG).show()
            CartManager.clearCart()
            finish()
        }

        // Back button
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
}
