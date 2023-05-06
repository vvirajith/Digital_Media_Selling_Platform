package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityCategoryAddBinding
import com.example.myapplication.databinding.ActivityCategoryDisplayBinding
import com.google.firebase.auth.FirebaseAuth

class CategoryDisplay : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryDisplayBinding
    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryDisplayBinding.inflate(layoutInflater)

        setContentView(binding.root)





        firebaseAuth = FirebaseAuth.getInstance()
    }
}