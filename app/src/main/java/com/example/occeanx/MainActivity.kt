package com.example.occeanx

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.occeanx.adapter.OrderAdapter
import com.example.occeanx.model.Order
import com.example.occeanx.model.OrderStatus

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: OrderAdapter
    private val allOrders = mutableListOf<Order>()

    private lateinit var tabAll: TextView
    private lateinit var tabCompleted: TextView
    private lateinit var tabCancelled: TextView
    private lateinit var tabBooked: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupOrders()
        setupRecyclerView()
        setupTabs()
        setupSearch()
        setupInfoBanner()
        setupBottomNav()
        setupFilterSort()
        setupHelpFab()
    }

    private fun setupOrders() {
        allOrders.addAll(listOf(
            Order("1", "Four Wheeler", "05 Feb, 4:46 PM", "#ORD12345",
                "741, Gumanwara",
                "00, Main Rd, Shivaji Nagar, Jhansi, Uttar Pradesh 284001, India",
                "₹ 229.0", OrderStatus.CANCELLED),
            Order("2", "Four Wheeler", "05 Feb, 4:46 PM", "#ORD12346",
                "741, Gumanwara",
                "00, Main Rd, Shivaji Nagar, Jhansi, Uttar Pradesh 284001, India",
                "₹ 229.0", OrderStatus.CANCELLED),
            Order("3", "Four Wheeler", "05 Feb, 4:46 PM", "#ORD12347",
                "332, Gumanwara",
                "GC72+GGV, Kamrari, Madhya Pradesh 475661, India",
                "₹ 1515.0", OrderStatus.CANCELLED),
            Order("4", "Four Wheeler", "05 Feb, 4:46 PM", "#ORD12348",
                "332, Gumanwara",
                "GC72+GGV, Kamrari, Madhya Pradesh 475661, India",
                "₹ 1634.0", OrderStatus.COMPLETED),
            Order("5", "Two Wheeler", "06 Feb, 10:00 AM", "#ORD12349",
                "MG Road, Bangalore",
                "Koramangala, Bangalore, Karnataka 560034, India",
                "₹ 120.0", OrderStatus.COMPLETED),
            Order("6", "Four Wheeler", "07 Feb, 2:30 PM", "#ORD12350",
                "Sector 18, Noida",
                "Connaught Place, New Delhi 110001, India",
                "₹ 850.0", OrderStatus.BOOKED_AGAIN)
        ))
    }

    private fun setupRecyclerView() {
        val recycler = findViewById<RecyclerView>(R.id.recyclerOrders)
        adapter = OrderAdapter(allOrders)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        recycler.isNestedScrollingEnabled = false
    }

    private fun setupTabs() {
        tabAll = findViewById(R.id.tabAll)
        tabCompleted = findViewById(R.id.tabCompleted)
        tabCancelled = findViewById(R.id.tabCancelled)
        tabBooked = findViewById(R.id.tabBooked)

        val tabs = listOf(tabAll, tabCompleted, tabCancelled, tabBooked)

        fun selectTab(selected: TextView, filter: (Order) -> Boolean) {
            tabs.forEach { tab ->
                if (tab == selected) {
                    tab.background = ContextCompat.getDrawable(this, R.drawable.bg_tab_selected)
                    tab.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
                    tab.setTypeface(null, android.graphics.Typeface.BOLD)
                } else {
                    tab.background = ContextCompat.getDrawable(this, R.drawable.ripple_tab)
                    tab.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
                    tab.setTypeface(null, android.graphics.Typeface.NORMAL)
                }
            }
            adapter.updateList(allOrders.filter(filter))
        }

        tabAll.setOnClickListener { selectTab(tabAll) { true } }
        tabCompleted.setOnClickListener {
            selectTab(tabCompleted) { it.status == OrderStatus.COMPLETED }
        }
        tabCancelled.setOnClickListener {
            selectTab(tabCancelled) { it.status == OrderStatus.CANCELLED }
        }
        tabBooked.setOnClickListener {
            selectTab(tabBooked) { it.status == OrderStatus.BOOKED_AGAIN }
        }
    }

    private fun setupSearch() {
        val etSearch = findViewById<EditText>(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase()
                val filtered = allOrders.filter {
                    it.orderId.lowercase().contains(query) ||
                    it.pickup.lowercase().contains(query) ||
                    it.drop.lowercase().contains(query)
                }
                adapter.updateList(filtered)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupInfoBanner() {
        val banner = findViewById<LinearLayout>(R.id.infoBanner)
        val closeBtn = findViewById<View>(R.id.btnCloseBanner)
        closeBtn.setOnClickListener {
            banner.visibility = View.GONE
        }
    }

    private fun setupBottomNav() {
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.navOrders).setOnClickListener {
            // Already on Orders screen
        }
        findViewById<LinearLayout>(R.id.navPayments).setOnClickListener {
            Toast.makeText(this, "Payments", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.navAccount).setOnClickListener {
            Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupFilterSort() {
        findViewById<LinearLayout>(R.id.btnFilter).setOnClickListener {
            Toast.makeText(this, "Filter options", Toast.LENGTH_SHORT).show()
        }
        findViewById<LinearLayout>(R.id.btnSort).setOnClickListener {
            Toast.makeText(this, "Sort options", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupHelpFab() {
        findViewById<LinearLayout>(R.id.fabHelp).setOnClickListener {
            Toast.makeText(this, "Help & Support", Toast.LENGTH_SHORT).show()
        }
    }
}
