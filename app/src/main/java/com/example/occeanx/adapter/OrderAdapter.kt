package com.example.occeanx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.occeanx.R
import com.example.occeanx.model.Order
import com.example.occeanx.model.OrderStatus

class OrderAdapter(private var orders: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvVehicleType: TextView = itemView.findViewById(R.id.tvVehicleType)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvOrderId: TextView = itemView.findViewById(R.id.tvOrderId)
        val tvPickup: TextView = itemView.findViewById(R.id.tvPickup)
        val tvDrop: TextView = itemView.findViewById(R.id.tvDrop)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val btnInvoice: LinearLayout = itemView.findViewById(R.id.btnInvoice)
        val btnBookAgain: LinearLayout = itemView.findViewById(R.id.btnBookAgain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        val ctx = holder.itemView.context

        holder.tvVehicleType.text = order.vehicleType
        holder.tvDate.text = order.date
        holder.tvOrderId.text = "Order ID: ${order.orderId}"
        holder.tvPickup.text = order.pickup
        holder.tvDrop.text = order.drop
        holder.tvPrice.text = order.price

        when (order.status) {
            OrderStatus.CANCELLED -> {
                holder.tvStatus.text = "CANCELLED"
                holder.tvStatus.setTextColor(ContextCompat.getColor(ctx, R.color.cancelled_red))
                holder.tvStatus.background = ContextCompat.getDrawable(ctx, R.drawable.bg_cancelled_badge)
            }
            OrderStatus.COMPLETED -> {
                holder.tvStatus.text = "COMPLETED"
                holder.tvStatus.setTextColor(ContextCompat.getColor(ctx, R.color.green_location))
                holder.tvStatus.background = ContextCompat.getDrawable(ctx, R.drawable.bg_completed_badge)
            }
            OrderStatus.BOOKED_AGAIN -> {
                holder.tvStatus.text = "BOOKED AGAIN"
                holder.tvStatus.setTextColor(ContextCompat.getColor(ctx, R.color.yellow_dark))
                holder.tvStatus.background = ContextCompat.getDrawable(ctx, R.drawable.bg_tab_selected)
            }
        }

        holder.btnInvoice.setOnClickListener {
            Toast.makeText(ctx, "Downloading invoice for ${order.orderId}", Toast.LENGTH_SHORT).show()
        }

        holder.btnBookAgain.setOnClickListener {
            Toast.makeText(ctx, "Booking again: ${order.vehicleType}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = orders.size

    fun updateList(newOrders: List<Order>) {
        orders = newOrders
        notifyDataSetChanged()
    }
}
