package com.example.occeanx.model

data class Order(
    val id: String,
    val vehicleType: String,
    val date: String,
    val orderId: String,
    val pickup: String,
    val drop: String,
    val price: String,
    val status: OrderStatus
)

enum class OrderStatus {
    CANCELLED, COMPLETED, BOOKED_AGAIN
}
