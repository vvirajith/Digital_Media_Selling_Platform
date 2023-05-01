package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val submitLogin = findViewById<Button>(R.id.submitLoginButton)

        submitLogin.setOnClickListener {
            val nextPage = Intent(this, Home::class.java)
            startActivity(nextPage);

        }

        val RegNow = findViewById<Button>(R.id.regNowButton);

        RegNow.setOnClickListener {
            val nextPage = Intent(this, RegisterAs::class.java)
            startActivity(nextPage)

        }


    }
}