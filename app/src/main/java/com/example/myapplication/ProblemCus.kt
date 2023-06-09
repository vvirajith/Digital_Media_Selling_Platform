package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ProblemCus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem_cus)

        val submitReview = findViewById<Button>(R.id.tempbutton);

        submitReview.setOnClickListener {
            val nextPage = Intent(this, CategoryDisplay::class.java);
            startActivity(nextPage);
        }

        val dashNext = findViewById<Button>(R.id.dashCusNextButton);
        dashNext.setOnClickListener {

            val nextPage = Intent(this, Login::class.java);
            startActivity(nextPage);
            finish();

        }
    }
}