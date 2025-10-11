package com.example.myapplication

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addToCart(item: CartItem) {
        val existingItem = cartItems.find { it.name == item.name && it.size == item.size }
        if (existingItem != null) {
            existingItem.quantity += item.quantity
        } else {
            cartItems.add(item)
        }
    }

    fun getCartItems(): List<CartItem> {
        return cartItems
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.price * it.quantity }
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun removeFromCart(item: CartItem) {
        val existingItem = cartItems.find { it.name == item.name && it.size == item.size }
        if (existingItem != null) {
            cartItems.remove(existingItem)
        }
    }

}
