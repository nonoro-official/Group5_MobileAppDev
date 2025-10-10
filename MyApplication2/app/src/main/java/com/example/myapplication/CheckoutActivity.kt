package com.example.myapplication

import android.content.Intent
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

        // Payment Dialog
        confirmButton.setOnClickListener {
            showPaymentDialog()
        }

        // Back button
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }

    //CustomTextBox to ask for payment
    private fun showPaymentDialog() {
        val dialogView = layoutInflater.inflate(R.layout.activity_input_payment, null)
        val dialog = android.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val cartItems = CartManager.getCartItems()
        val total = cartItems.sumOf { it.price * it.quantity }

        val summaryText = dialogView.findViewById<TextView>(R.id.cartItemsSummary)
        val totalText = dialogView.findViewById<TextView>(R.id.totalAmountText)
        val paymentInput = dialogView.findViewById<EditText>(R.id.paymentInput)
        val confirmBtn = dialogView.findViewById<Button>(R.id.btnConfirmPayment)

        // Shows items in cart
        val summary = StringBuilder()
        for (item in cartItems) {
            summary.append("• ${item.name} (${item.size}) x${item.quantity} - ₱%.2f\n".format(item.price * item.quantity))
        }

        summaryText.text = summary.toString()
        totalText.text = "Total: ₱%.2f".format(total)

        // Checks Payment Amount
        confirmBtn.setOnClickListener {
            val payment = paymentInput.text.toString().toDoubleOrNull()

            if (payment == null) {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (payment < total) {
                Toast.makeText(this, "Insufficient funds!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Calculate change and build receipt
            val change = payment - total
            val receipt = StringBuilder("✅ Payment Successful!\n\nItems Purchased:\n")
            for (item in cartItems) {
                receipt.append("• ${item.name} (${item.size}) x${item.quantity}\n")
            }
            receipt.append("\nTotal: ₱%.2f\nPaid: ₱%.2f\nChange: ₱%.2f".format(total, payment, change))

            dialog.dismiss()

            // Display receipt
            val receiptDialog = android.app.AlertDialog.Builder(this)
                .setTitle("Receipt")
                .setMessage(receipt.toString())
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    // Cart Clear, redirect back to menu
                    CartManager.clearCart()
                    val intent = Intent(this, MenuActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                .create()

            receiptDialog.show()
        }

        dialog.show()
    }
}
