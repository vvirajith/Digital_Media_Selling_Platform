package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ProfileCustomer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_customer)

        val viewInventory = findViewById<Button>(R.id.viewInventoryButtton);
        viewInventory.setOnClickListener {
            val nextPage = Intent(this, InventoryCustomer::class.java);
            startActivity(nextPage);
            finish();
        }


        val modifyProfile = findViewById<Button>(R.id.modifyProfileButton);
        modifyProfile.setOnClickListener {
            val nextPage = Intent(this, Login::class.java);
            startActivity(nextPage);
            finish();
        }

    }
}