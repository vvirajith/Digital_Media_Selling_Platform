package com.example.myapplication

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val login = findViewById<Button>(R.id.loginButton);

        login.setOnClickListener {
            val nextPage = Intent(this, Login::class.java);
            startActivity(nextPage);
        }


        val viewItem = findViewById<Button>(R.id.viewItemHomeButton);

        viewItem.setOnClickListener {
            val nextPage = Intent(this, ItemView::class.java);
            startActivity(nextPage);
        }

    }
}