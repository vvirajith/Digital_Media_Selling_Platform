package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ModifyProfileCus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_profile_cus)

        val backModifyProfile = findViewById<Button>(R.id.cancelUpdateProfile);

        backModifyProfile.setOnClickListener {
            val nextPage = Intent(this, ProfileCustomer::class.java);
            startActivity(nextPage);
        }

        val updateModifyProfile = findViewById<Button>(R.id.updateProfile);

        updateModifyProfile.setOnClickListener {
            val nextPage = Intent(this, ProfileCustomer::class.java);
            startActivity(nextPage);
        }
    }
}