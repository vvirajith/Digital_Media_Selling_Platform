package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ProfileShop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_profile)

        val addCusCategory = findViewById<Button>(R.id.addCutomCatButton);
        addCusCategory .setOnClickListener {
            val nextPage = Intent(this, CusCategoryAdd::class.java);
            startActivity(nextPage);
            finish();
        }

        val addItem = findViewById<Button>(R.id.addPrButton);
        addItem .setOnClickListener {
            val nextPage = Intent(this, ItemAdd::class.java);
            startActivity(nextPage);
            finish();
        }


        val viewMyCat = findViewById<Button>(R.id.viewCutomCatButton);
        viewMyCat.setOnClickListener {
            val nextPage = Intent(this, MyCatView::class.java);
            startActivity(nextPage);
            finish();
        }

        val viewAllItems = findViewById<Button>(R.id.viewItemButton);
        viewAllItems.setOnClickListener {
            val nextPage = Intent(this, ViewItemAll::class.java);
            startActivity(nextPage);
            finish();
        }

        val modifyProfileShop = findViewById<Button>(R.id.modifyProfileButton);
        modifyProfileShop.setOnClickListener {
            val nextPage = Intent(this, ModifyProfileShop::class.java);
            startActivity(nextPage);
            finish();
        }


    }
}