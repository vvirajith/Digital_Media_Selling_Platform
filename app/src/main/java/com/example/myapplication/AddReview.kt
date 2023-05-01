package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddReview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_review)


        val submitReview = findViewById<Button>(R.id.submitReviewButton);

        submitReview.setOnClickListener {
            val nextPage = Intent(this, MainActivity::class.java);
            startActivity(nextPage);
        }

        val cancelReview = findViewById<Button>(R.id.cancelReviewButton);

        cancelReview.setOnClickListener {
            val nextPage = Intent(this, MainActivity::class.java);
            startActivity(nextPage);
        }
    }
}