package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
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

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCart)
        totalPriceText = findViewById(R.id.textTotal)
        checkoutButton = findViewById(R.id.btnCheckout)

        val cartItems = CartManager.getCartItems().toMutableList()

        cartAdapter = CartAdapter(cartItems) {
            updateTotal()
        }

        recyclerView.adapter = cartAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        updateTotal()

        checkoutButton.setOnClickListener {
            if (cartItems.isNotEmpty()) {
                startActivity(Intent(this, CheckoutActivity::class.java))
            }
        }

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener { finish() }
    }

    private fun updateTotal() {
        val total = CartManager.getCartItems().sumOf { it.price * it.quantity }
        totalPriceText.text = "Total: ₱%.2f".format(total)
        checkoutButton.isEnabled = total > 0
    }


}
