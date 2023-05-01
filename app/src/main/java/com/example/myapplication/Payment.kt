package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Payment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val submitPayment = findViewById<Button>(R.id.submitPayment);

        submitPayment.setOnClickListener {
            val nextPage = Intent(this, ItemDownload::class.java);
            startActivity(nextPage);
        }

        val cancelPayment = findViewById<Button>(R.id.cancelPayment);

        submitPayment.setOnClickListener {
            val nextPage = Intent(this, ItemView::class.java);
            startActivity(nextPage);
        }


    }
}