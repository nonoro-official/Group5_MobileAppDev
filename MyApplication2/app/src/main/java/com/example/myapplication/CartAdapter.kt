package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val cartItems: List<CartItem>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.findViewById(R.id.textCartItemName)
        val textQuantity: TextView = view.findViewById(R.id.textCartItemQty)
        val textPrice: TextView = view.findViewById(R.id.textCartItemPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_row, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.textName.text = item.name
        holder.textQuantity.text = "x${item.quantity}"
        holder.textPrice.text = "₱%.2f".format(item.price * item.quantity)
    }

    override fun getItemCount(): Int = cartItems.size
}
