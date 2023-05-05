package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MyCatView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cat_view)



        val backMyCat = findViewById<Button>(R.id.button20);

        backMyCat.setOnClickListener {
            val nextPage = Intent(this, ProfileShop::class.java);
            startActivity(nextPage);
        }



    }
}