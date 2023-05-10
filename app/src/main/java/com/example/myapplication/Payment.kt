package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.ActivityItemAddBinding
import com.example.myapplication.databinding.ActivityPaymentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Payment : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //init // firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // configure progress dialog

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait....")
        progressDialog.setCanceledOnTouchOutside(false)





        val submitPayment = findViewById<Button>(R.id.submitPayment);

     submitPayment.setOnClickListener {
            validateData()

        }

        val cancelPayment = findViewById<Button>(R.id.cancelPayment);

        cancelPayment.setOnClickListener {
            val nextPage = Intent(this, NavigationBar::class.java);
            startActivity(nextPage);
        }


    }

    private var card_no = ""
    private var holder_name = ""
    private var exp_date =""
    private  var CVC=""
    private var email=""


    private fun validateData() {

        card_no =  binding.cardNumber.text.toString().trim()
        holder_name = binding.cardholdersName.text.toString().trim()
        exp_date =  binding.exppp.text.toString().trim()
        CVC = binding.cvc.text.toString().trim()
        email = binding.emailPay.text.toString().trim()


        //validate data
        if (card_no.isEmpty()) {
            Toast.makeText(this, "Enter No...", Toast.LENGTH_SHORT).show()
        }else if (holder_name.isEmpty()) {
            Toast.makeText(this, "Enter CardHolder name...", Toast.LENGTH_SHORT).show()
        }else if(exp_date.isEmpty()) {
            Toast.makeText(this, "Enter Exp Date...", Toast.LENGTH_SHORT).show()
        } else if(CVC.isEmpty()) {
            Toast.makeText(this, "Enter CVC...", Toast.LENGTH_SHORT).show()
        }
        else if(email.isEmpty()) {
            Toast.makeText(this, "Enter email...", Toast.LENGTH_SHORT).show()
        }else{
            AddPaymentFirebase()
        }



    }

    private fun AddPaymentFirebase (){

        //show progress
        progressDialog.show()

        //get timestamp
        val timestamp = System.currentTimeMillis()

        // setting up data to add in firebase DB
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["cardNo"]= card_no
        hashMap["holderName"]=holder_name
        hashMap["expireDate"]=exp_date
        hashMap["CVC"]=CVC
        hashMap["email"]=email
        hashMap["timestamp"] =timestamp
        hashMap["uid"] = "${firebaseAuth.uid}"


        //add to firebase DB: Database Root > categoryID  >category INfo
        val ref = FirebaseDatabase.getInstance().getReference("Payments")
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