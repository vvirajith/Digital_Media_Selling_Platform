package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.ActivityCategoryAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CategoryAdd : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryAddBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryAddBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //init // firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // configure progress dialog

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait....")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click , go back
        binding.addCategoryButton.setOnClickListener {
            onBackPressed()
        }

        //handle click,  begin upload category
        binding.addCategoryButton.setOnClickListener {
            validateData()
        }



    }

    private var category = ""

    private fun validateData() {
        //validate data

        //get data
        category = binding.categoryEt.text.toString().trim()

        //validate data
        if (category.isEmpty()) {
            Toast.makeText(this, "Enter Category...", Toast.LENGTH_SHORT).show()
        } else {
            addCategoryFirebase()

        }

    }

    private fun addCategoryFirebase() {
        //show progress
        progressDialog.show()

        //get timestamp
        val timestamp = System.currentTimeMillis()

        // setting up data to add in firebase DB
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["timestamp"] = category
        hashMap["uid"] = "${firebaseAuth.uid}"

        //add to firebase DB: Database Root > categoryID  >category INfo
        val ref = FirebaseDatabase.getInstance().getReference("categories")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnCompleteListener {
                // added successfully
                progressDialog.dismiss()
                Toast.makeText(this, "Added Successfully...", Toast.LENGTH_SHORT).show()


            }
            .addOnFailureListener {e->
                //failed to add
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()

            }


    }


}