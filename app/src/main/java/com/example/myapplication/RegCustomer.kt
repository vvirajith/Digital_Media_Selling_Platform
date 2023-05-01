package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegCustomer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg_customer)


        val cancelRegShopButton = findViewById<Button>(R.id.cancelRegCusButton);

        cancelRegShopButton.setOnClickListener {
            val nextPage = Intent(this, Login::class.java);
            startActivity(nextPage);
        }

        val regCusButton = findViewById<Button>(R.id.regCusButton);

        regCusButton.setOnClickListener {
            val nextPage = Intent(this, ProfileCustomer::class.java);
            startActivity(nextPage);
        }

    }
}