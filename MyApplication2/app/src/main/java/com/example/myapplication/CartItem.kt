package com.example.myapplication

data class CartItem(
    val name: String,
    val size: String,
    val price: Double,
    var quantity: Int = 1
)
