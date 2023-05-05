package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ModifyProfileShop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_profile_shop)

        val modifyRegShop = findViewById<Button>(R.id.modifyShopButton);

        modifyRegShop.setOnClickListener {
            val nextPage = Intent(this, ProfileShop::class.java);
            startActivity(nextPage);
        }

        val cancelmodifyRegShop = findViewById<Button>(R.id.cancelModifyShopButton);

        cancelmodifyRegShop.setOnClickListener {
            val nextPage = Intent(this, ProfileShop::class.java);
            startActivity(nextPage);
        }

    }
}