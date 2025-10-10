package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartViewerActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var totalPriceText: TextView
    private lateinit var checkoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_viewer)

        // Updated IDs to match your XML
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCart)
        totalPriceText = findViewById(R.id.textTotal)
        checkoutButton = findViewById(R.id.btnCheckout)

        // Get cart items
        val cartItems = CartManager.getCartItems().toMutableList()

        // Setup RecyclerView
        cartAdapter = CartAdapter(cartItems)
        recyclerView.adapter = cartAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Show total price
        val total = cartItems.sumOf { it.price * it.quantity }
        totalPriceText.text = "Total: ₱%.2f".format(total)

        // Enable or disable checkout button if cart is empty
        checkoutButton.isEnabled = cartItems.isNotEmpty()

        // Proceed to checkout
        checkoutButton.setOnClickListener {
            if (cartItems.isNotEmpty()) {
                val intent = Intent(this, CheckoutActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
