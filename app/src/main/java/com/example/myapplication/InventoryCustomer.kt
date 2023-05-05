package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class InventoryCustomer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_customer)

        val backInventory = findViewById<Button>(R.id.BackInventoryButton);

        backInventory.setOnClickListener {
            val nextPage = Intent(this, ProfileCustomer::class.java);
            startActivity(nextPage);
        }
    }
}