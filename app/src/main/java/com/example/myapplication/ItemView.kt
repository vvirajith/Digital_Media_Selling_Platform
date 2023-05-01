package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ItemView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_view)

        val modifyItem = findViewById<Button>(R.id.modifyItemButton);

        modifyItem.setOnClickListener {
            val nextPage = Intent(this, ItemView::class.java);
            startActivity(nextPage);
        }


        val addReview = findViewById<Button>(R.id.addReviewButton);

        addReview.setOnClickListener {
            val nextPage = Intent(this, AddReview::class.java);
            startActivity(nextPage);
        }

        val allReview = findViewById<Button>(R.id.allReviewsButton);

        allReview.setOnClickListener {
            val nextPage = Intent(this, AllShopReveiws::class.java);
            startActivity(nextPage);
        }



    }
}