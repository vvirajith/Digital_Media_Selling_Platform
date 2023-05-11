package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CatView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_view)

        val addCategory = findViewById<Button>(R.id.addCatBtn);

        addCategory.setOnClickListener {
            val nextPage = Intent(this, CategoryAdd::class.java);
            startActivity(nextPage);
        }
    }
}