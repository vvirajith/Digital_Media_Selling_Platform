package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.ActivityAddReviewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddReview : AppCompatActivity() {
    private lateinit var binding: ActivityAddReviewBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReviewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //init // firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // configure progress dialog

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait....")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click , go back
        binding.addReviewBtn.setOnClickListener {
            onBackPressed()
        }

        //handle click,  begin upload category
        binding.addReviewBtn
            .setOnClickListener {
            validateData()
        }


        val cancelReview = findViewById<Button>(R.id.cancelReviewButton);

        cancelReview.setOnClickListener {
            val nextPage = Intent(this, MainActivity::class.java);
            startActivity(nextPage);
        }
    }
    private var review = ""

    private fun validateData() {
        review = binding.addReviewBttn.text.toString().trim()

        //validate data
        if (review.isEmpty()) {
            Toast.makeText(this, "Enter Category...", Toast.LENGTH_SHORT).show()
        } else {
            addReviewFirebase()

        }
    }

    private fun addReviewFirebase() {
        //show progress
        progressDialog.show()

        //get timestamp
        val timestamp = System.currentTimeMillis()

        // setting up data to add in firebase DB
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["review"]= review
        hashMap["timestamp"] = timestamp
        hashMap["uid"] = "${firebaseAuth.uid}"

        //add to firebase DB: Database Root > categoryID  >category INfo
        val ref = FirebaseDatabase.getInstance().getReference("review")
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