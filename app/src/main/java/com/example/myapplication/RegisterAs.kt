package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegisterAs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_as)

        val regAsCusButton = findViewById<Button>(R.id.regAsCusButton);

        regAsCusButton.setOnClickListener {
            val nextPage = Intent(this, RegCustomer::class.java);
            startActivity(nextPage);
            finish();
        }

        val regAsShopButton = findViewById<Button>(R.id.regAsShopButton);

        regAsShopButton.setOnClickListener {
            val nextPage = Intent(this, RegShop::class.java);
            startActivity(nextPage);
            finish();
        }


    }
}