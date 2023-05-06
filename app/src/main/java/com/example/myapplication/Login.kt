package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.regex.Pattern

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.signin.setOnClickListener{
            val nextPage = Intent(this, RegCustomer::class.java)
            startActivity(nextPage)
        }

        binding.submitLoginButton.setOnClickListener{
            validateData()
        }


        val regNow = findViewById<Button>(R.id.regNowButton)

        regNow.setOnClickListener {
            val nextPage = Intent(this, RegisterAs::class.java)
            startActivity(nextPage)

        }



    }

    private var email = ""
    private var pass = ""
    private fun validateData() {
        email = binding.email.text.toString()
        pass = binding.passwordCus.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Invalid Email format..", Toast.LENGTH_SHORT).show()
        }

        if (pass.isEmpty()){
            Toast.makeText(this,"Please Enter Password..", Toast.LENGTH_SHORT).show()
        }
        if (email.isNotEmpty() && pass.isNotEmpty()){
            loginUser()
        }else{
            Toast.makeText(this,"Empty Fields Are Not Allowed!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser() {
        progressDialog.setMessage("Loggin In..")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email,pass)
            .addOnSuccessListener{
                checkUser()
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun checkUser() {
        progressDialog.setMessage("Checking User...")

        val firebaseUser = firebaseAuth.currentUser!!

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()
                    val userType= snapshot.child("userType").value
                    if (userType == "user"){
                        startActivity(Intent(this@Login, Blank::class.java))
                        finish()
                    }
                    else if (userType == "admin"){
                        startActivity(Intent(this@Login, NavigationBar::class.java))
                        finish()
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }


            } )

    }

    override fun onStart(){
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, NavigationBar::class.java)
            startActivity(intent)
        }

    }
}