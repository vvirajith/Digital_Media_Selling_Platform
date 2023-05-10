package com.example.myapplication

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityCategoryAddBinding
import com.example.myapplication.databinding.ActivityProblemAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProblemAdd : AppCompatActivity() {
    private lateinit var binding: ActivityProblemAddBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProblemAddBinding.inflate(layoutInflater)


        setContentView(binding.root)

        //init // firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // configure progress dialog

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait....")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click , go back
        binding.addPrButton.setOnClickListener {
            onBackPressed()
        }

        //handle click,  begin upload category
        binding.addPrButton.setOnClickListener {
            validateData()

        }


    }

    private var category = ""

    private fun validateData() {
        //validate data

        //get data
        category = binding.problemEt.text.toString().trim()

        //validate data
        if (category.isEmpty()) {
            Toast.makeText(this, "Enter Category...", Toast.LENGTH_SHORT).show()
        } else {
            addProblemFirebase()

        }

    }

        private fun addProblemFirebase() {
            //show progress
            progressDialog.show()

            //get timestamp
            val timestamp = System.currentTimeMillis()

            // setting up data to add in firebase DB
            val hashMap = HashMap<String, Any>()
            hashMap["id"] = "$timestamp"
            hashMap["problem"]= category
            hashMap["timestamp"] = timestamp
            hashMap["uid"] = "${firebaseAuth.uid}"

            //add to firebase DB: Database Root > problemID  >problem INfo
            val ref = FirebaseDatabase.getInstance().getReference("problem")
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
