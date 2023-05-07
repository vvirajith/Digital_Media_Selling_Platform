package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.ActivityRegCustomerBinding
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.fragments.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegCustomer : AppCompatActivity() {

    private lateinit var binding: ActivityRegCustomerBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.regCusButton.setOnClickListener {
            validateData()
        }

        val cancelRegShopButton = findViewById<Button>(R.id.cancelRegCusButton);

        cancelRegShopButton.setOnClickListener {
            val nextPage = Intent(this, Login::class.java);
            startActivity(nextPage);
        }
    }

    private var fname = ""
    private var lname = ""
    private var email = ""
    private var pass = ""

    private fun validateData() {
        fname = binding.cusFName.text.toString()
        lname = binding.cusLName.text.toString()
        email = binding.emailCus.text.toString()
        pass = binding.passwordCus.text.toString()
        val confirmPass = binding.rePasswordCus.text.toString()


        if (fname.isEmpty()){
            Toast.makeText(this, "Enter Your first name!", Toast.LENGTH_SHORT).show()
        } else if (lname.isEmpty()){
            Toast.makeText(this, "Enter Your last name!", Toast.LENGTH_SHORT).show()
        } else if (pass.isEmpty()){
            Toast.makeText(this, "Enter Your Password!", Toast.LENGTH_SHORT).show()
        } else if (confirmPass.isEmpty()){
            Toast.makeText(this, "Confirm Your Password!", Toast.LENGTH_SHORT).show()
        } else if(pass != confirmPass) {
            Toast.makeText(this, "Password mismatched!", Toast.LENGTH_SHORT).show()
        }else {
            createUserAccount()
        }


    }

    private fun createUserAccount() {
        firebaseAuth.createUserWithEmailAndPassword(email, pass,)
            .addOnSuccessListener {
                    updateUserInfo()
            }
            .addOnFailureListener{ e ->
                progressDialog.dismiss()
                Toast.makeText(this,"Failed creating account due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInfo() {
        progressDialog.setMessage("Saving user info...")
        val timestamp = System.currentTimeMillis()

        val uid = firebaseAuth.uid

        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["fname"]= fname
        hashMap["lname"] = lname
        hashMap["profileImg"]= ""
        hashMap["userType"] = "user"
        hashMap["timestamp"]= timestamp

        //send data to db
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Account created successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this,"Failed saving user info due to ${e.message}", Toast.LENGTH_SHORT).show()

            }


    }

}