package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegShop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg_shop)


        val cancelRegShop = findViewById<Button>(R.id.cancelRegShopButton);

        cancelRegShop.setOnClickListener {
            val nextPage = Intent(this, Login::class.java);
            startActivity(nextPage);
        }

        val regShop = findViewById<Button>(R.id.regShopButton);

        regShop.setOnClickListener {
            val nextPage = Intent(this, ProfileShop::class.java);
            startActivity(nextPage);
        }

    }
}